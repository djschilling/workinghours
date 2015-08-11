package de.schilling.workinghours.duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author David Schilling - davejs92@gmail.com
 */
@Service
public class DurationValidationServiceImpl implements DurationValidationService {

    private final DurationRepository durationRepository;

    @Autowired
    public DurationValidationServiceImpl(DurationRepository durationRepository) {
        this.durationRepository = durationRepository;
    }

    @Override
    public boolean validateNewDuration(Duration duration) {

        List<Duration> durationList = durationRepository.findByUsernameAndStartTimeBetweenOrderByStartTimeDesc
                (duration.getUsername(), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2100, 1, 1, 1, 1));
        for (Duration currentDuration : durationList) {
            if (currentDuration.getEndTime() == null) {
                if (!currentDuration.getStartTime().isAfter(duration.getEndTime()) &&
                        !currentDuration.getStartTime().isBefore(duration.getStartTime())) {
                    return false;
                }
            } else {
                if (duration.getEndTime() == null) {
                    if (!duration.getStartTime().isAfter(currentDuration.getEndTime()) &&
                            !duration.getStartTime().isBefore(currentDuration.getStartTime())) {
                        return false;
                    }
                } else {
                    if (!currentDuration.getStartTime().isAfter(duration.getEndTime()) &&
                            !currentDuration.getEndTime().isBefore(duration.getStartTime())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
