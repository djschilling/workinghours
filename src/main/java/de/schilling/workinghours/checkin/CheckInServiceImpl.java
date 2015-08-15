package de.schilling.workinghours.checkin;

import de.schilling.workinghours.duration.Duration;
import de.schilling.workinghours.duration.DurationService;
import de.schilling.workinghours.duration.DurationType;
import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * David Schilling - davejs92@gmail.com
 **/
@Transactional
@Service
public class CheckInServiceImpl implements CheckInService {

    private final CheckInRepository checkInRepository;
    private final DurationService durationService;
    private final UserService userService;

    @Autowired
    public CheckInServiceImpl(CheckInRepository checkInRepository, DurationService durationService, UserService userService) {
        this.checkInRepository = checkInRepository;
        this.durationService = durationService;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public CheckIn get(String username) {

        User user = userService.getCurrentlyLoggedIn();

        if(!user.getUsername().equals(username)) {
            throw new AccessDeniedException("Access denied for user " + user.getUsername() + " for checkins of user " + username);
        }

        CheckIn checkIn = checkInRepository.findByUsername(username);

        if (checkIn == null) {
            throw new CheckInServiceException("No checkin for user: " + username + " found");
        }
        return checkIn;
    }

    public void checkIn(CheckIn checkIn) {

        User user = userService.getCurrentlyLoggedIn();

        if(!user.getUsername().equals(checkIn.getUsername())) {
            throw new AccessDeniedException("Access denied for user " + user.getUsername() + " for checkins of user " + checkIn.getUsername());
        }
        if(checkInRepository.findByUsername(user.getUsername()) != null) {
            throw new CheckInServiceException(user.getUsername() + " is already checked in");
        }


        checkInRepository.save(checkIn);
    }

    public void checkOut(CheckOut checkOut) {
        User user = userService.getCurrentlyLoggedIn();

        if(!user.getUsername().equals(checkOut.getUsername())) {
            throw new AccessDeniedException("Access denied for user " + user.getUsername() + " for checkins of user " + checkOut.getUsername());
        }


        CheckIn checkIn = get(checkOut.getUsername());

        Duration duration = new Duration(checkIn.getStartTime(), checkOut.getEndTime(), checkOut.getUsername(), DurationType.WORK);

        durationService.create(duration);
        checkInRepository.delete(checkIn);

    }

}
