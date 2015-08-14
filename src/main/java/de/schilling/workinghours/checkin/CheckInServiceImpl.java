package de.schilling.workinghours.checkin;

import de.schilling.workinghours.duration.Duration;
import de.schilling.workinghours.duration.DurationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CheckInServiceImpl(CheckInRepository checkInRepository, DurationService durationService) {
        this.checkInRepository = checkInRepository;
        this.durationService = durationService;
    }

    @Override
    @Transactional(readOnly = true)
    public CheckIn get(String username) {
        CheckIn checkIn = checkInRepository.findByUsername(username);

        if (checkIn == null) {
            throw new CheckInServiceException("No checkin for user: " + username + " found");
        }
        return checkIn;
    }

    public void checkIn(CheckIn checkIn) {

        checkInRepository.save(checkIn);
    }

    public void checkOut(CheckOut checkOut) {

        CheckIn checkIn = get(checkOut.getUsername());

        Duration duration = new Duration(checkIn.getStartTime(), checkOut.getEndTime(), checkOut.getUsername());

        durationService.create(duration);
        checkInRepository.delete(checkIn);

    }

}
