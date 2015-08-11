package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.unmodifiableList;

/**
 * @author David Schilling - davejs92@gmail.com
 */
@Service
@Transactional
public class DurationServiceImpl implements DurationService {

    private final DurationRepository durationRepository;
    private final UserService userService;
    private final DurationValidationService durationValidationService;

    @Autowired
    public DurationServiceImpl(DurationRepository durationRepository, UserService userService, DurationValidationService durationValidationService) {

        this.durationRepository = durationRepository;
        this.userService = userService;
        this.durationValidationService = durationValidationService;
    }

    @Override
    public List<Duration> get(Integer year, Integer month) {

        LocalDateTime from = calculateStartOfMonth(year, month);
        LocalDateTime to = from.plusMonths(1).minusNanos(1);

        return unmodifiableList(durationRepository.findByStartTimeBetweenOrderByStartTimeDesc(from, to));
    }

    @Override
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

    @Override
    public Duration create(Duration duration) {
        duration.setUsername(userService.getCurrentlyLoggedIn().getUsername());
        duration.setId(null);
        if (!durationValidationService.validateNewDuration(duration)) {
            throw new InvalidDurationException("Duration collidates with other duration.");
        }
        return durationRepository.save(duration);
    }

    @Override
    public Duration update(Duration duration, Long id) {

        Duration previous = durationRepository.getOne(id);
        duration.setUsername(previous.getUsername());
        duration.setId(id);

        User user = userService.getCurrentlyLoggedIn();
        if (!user.getAuthorities().contains("ROLE_ADMIN") && !user.getUsername().equals(duration.getUsername())) {
            throw new AccessDeniedException("Duration cannot be updated by other User");
        }

        durationRepository.save(duration);
        return duration;
    }

    @Override
    public Duration get(Long id) {
        User user = userService.getCurrentlyLoggedIn();
        Duration duration = durationRepository.findById(id);
        if (duration == null) {
            return null;// TODO
        }
        if (!user.getAuthorities().contains("ROLE_ADMIN") && !user.getUsername().equals(duration.getUsername())) {
            throw new AccessDeniedException("Access denied for user " + user.getUsername() + " for duration with id " + id);
        }
        return duration;
    }

    @Override
    public void delete(Long id) {
        Duration one = durationRepository.findById(id);

        User user = userService.getCurrentlyLoggedIn();

        if (!one.getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("Access denied for user " + user.getUsername() + " for duration with id " + id);
        }
        durationRepository.delete(id);
    }

    private LocalDateTime calculateStartOfMonth(Integer year, Integer month) {
        LocalDateTime startOfMonth;
        if (year != null && month != null) {
            startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        } else {
            LocalDateTime now = LocalDateTime.now();
            startOfMonth = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
        }
        return startOfMonth;
    }

}
