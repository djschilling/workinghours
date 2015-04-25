package de.schilling.workinghours.duration;

import java.util.List;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public interface DurationService {

    /**
     * Retrieves {@link de.schilling.workinghours.duration.Duration}s.
     * Only users with role admin have the right to access this method.
     *
     * @param year the year to filter the durations.
     * @param month the month to filter the durations.
     *
     * @return a list of {@link de.schilling.workinghours.duration.Duration}s.
     *
     * @throws org.springframework.security.access.AccessDeniedException if user without role admin tries to access
     *  this method.
     */
    List<Duration> get(Integer year, Integer month);

    /**
     * Retrieves all {@link de.schilling.workinghours.duration.Duration}s for the given user.
     * Only users with role admin or the user with the given username have the right to access this method.
     *
     * @return a list of {@link de.schilling.workinghours.duration.Duration}s.
     *
     * @throws org.springframework.security.access.AccessDeniedException if user without role admin and not the
     *  given username to access this method.
     */
    List<Duration> get(String username, Integer year, Integer month);


    /**
     * Calculates the sum of all passed {@link de.schilling.workinghours.duration.Duration}s.
     *
     * @param durations a list of {@link de.schilling.workinghours.duration.Duration}s for which the sum should
     *                  be calculated.
     * @return the calculation result as long.
     */
    long calculateDurationSum(List<Duration> durations);

    /**
     * Saves the given {@link de.schilling.workinghours.duration.Duration} for the logged in user.
     *
     * @param duration the {@link de.schilling.workinghours.duration.Duration} object to save.
     *
     * @return the saved {@link de.schilling.workinghours.duration.Duration}.
     */
    Duration save(Duration duration);
}
