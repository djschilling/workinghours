package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserService;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * David Schilling - davejs92@gmail.com
 */
@Service
public class DurationService {

    private DurationRepository durationRepository;
    private UserService userService;

    @Autowired
    public DurationService(DurationRepository durationRepository, UserService userService) {
        this.durationRepository = durationRepository;
        this.userService = userService;
    }

    public Duration save(Duration duration) {
        duration.setUsername(userService.getCurrentlyLoggedIn().getUsername());
        return durationRepository.save(duration);
    }

    public List<Duration> getAllForCurrentUser() {

        User user = userService.getCurrentlyLoggedIn();
        if (user.getRole().equals("admin")) {
            return durationRepository.findAll();
        }
        return durationRepository.findByUsername(user.getUsername());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Duration> getAllForUser(String username) {
        return durationRepository.findByUsername(username);
    }

    public long calculateDurationSum(List<Duration> durationList) {

        return durationList.stream().mapToLong(d->d.getDuration()).sum();
    }
}
