package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<Duration> get(Integer year, Integer month) {

        LocalDateTime from = calculateStartOfMonth(year, month);
        LocalDateTime to = from.plusMonths(1).minusNanos(1);

        return unmodifiableList(durationRepository.findByStartTimeBetweenOrderByStartTimeDesc(from, to));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == principal.username")
    public List<Duration> get(String username, Integer year, Integer month) {

        LocalDateTime from = calculateStartOfMonth(year, month);
        LocalDateTime to = from.plusMonths(1).minusNanos(1);

        return unmodifiableList(durationRepository.findByUsernameAndStartTimeBetweenOrderByStartTimeDesc(username, from, to));
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

    private LocalDateTime calculateStartOfMonth(Integer year, Integer month) {
        LocalDateTime startOfMonth;
        if(year != null && month != null) {
            startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        } else {
            LocalDateTime now = LocalDateTime.now();
            startOfMonth = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
        }
        return startOfMonth;
    }
}
