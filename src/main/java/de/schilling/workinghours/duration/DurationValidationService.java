package de.schilling.workinghours.duration;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public interface DurationValidationService {

    boolean validateAgainstExsitingDurations(Duration duration);
    boolean validateDurationLenth(Duration duration);
}
