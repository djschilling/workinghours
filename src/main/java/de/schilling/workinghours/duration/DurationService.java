package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public List<Duration> getTimefilteredForCurrentUser(String from, String to) {
        LocalDateTime startDateTime = parseStartDate(from);
        LocalDateTime endDateTime = parseEndDate(to);
        User user = userService.getCurrentlyLoggedIn();
        if (user.getRole().equals("admin")) {
            return filterBefore(filterAfter(durationRepository.findAll(), startDateTime), endDateTime);
        }
        return filterBefore(filterAfter(durationRepository.findByUsername(user.getUsername()), startDateTime), endDateTime);
    }

    private LocalDateTime parseEndDate(String to) {
        if(to == null) {
            return LocalDateTime.of(LocalDate.MAX, LocalTime.MAX);
        } else {
            return LocalDateTime.of(LocalDate.parse(to), LocalTime.MAX);
        }
    }

    private LocalDateTime parseStartDate(String from) {
        if(from == null) {
            return LocalDateTime.of(LocalDate.MIN, LocalTime.MIN);
        } else {
            return LocalDateTime.of(LocalDate.parse(from), LocalTime.MIN);
        }
    }

    private List<Duration> filterBefore(List<Duration> all, LocalDateTime endDateTime) {
        List<Duration> before = new ArrayList<>();
        for(Duration currentDuration : all) {
            if(currentDuration.getStartTime() != null) {
                if (currentDuration.getStartTime().isBefore(endDateTime)) {
                    before.add(currentDuration);
                }
            } else {
                before.add(currentDuration);
            }
        }
        return before;    }

    private List<Duration> filterAfter(List<Duration> all, LocalDateTime startDateTime) {
        List<Duration> after = new ArrayList<>();
        for(Duration currentDuration : all) {
            if(currentDuration.getStartTime() != null) {
                if (currentDuration.getStartTime().isAfter(startDateTime)) {
                    after.add(currentDuration);
                }
            } else {
                after.add(currentDuration);
            }
        }
        return after;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Duration> getTimefilteredForUser(String username, String from, String to) {
        LocalDateTime startDateTime = parseStartDate(from);
        LocalDateTime endDateTime = parseEndDate(to);
        return filterBefore(filterAfter(durationRepository.findByUsername(username), startDateTime), endDateTime);
    }

    public long calculateDurationSum(List<Duration> durationList) {

        return durationList.stream().mapToLong(d->d.getDuration()).sum();
    }
}
