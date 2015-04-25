package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @author David Schilling - davejs92@gmail.com
 */
@Service
public class DurationServiceImpl implements DurationService {

    private DurationRepository durationRepository;
    private UserService userService;

    @Autowired
    public DurationServiceImpl(DurationRepository durationRepository, UserService userService) {

        this.durationRepository = durationRepository;
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Duration> get() {

        return unmodifiableList(durationRepository.findAll());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == principal.username")
    public List<Duration> get(String username) {

        return unmodifiableList(durationRepository.findByUsername(username));
    }

    @Override
    public long calculateDurationSum(List<Duration> durations) {

        return durations.stream().
                mapToLong(Duration::getDuration).
                sum();
    }

    public Duration save(Duration duration) {

        duration.setUsername(userService.getCurrentlyLoggedIn().getUsername());
        return durationRepository.save(duration);
    }
}
