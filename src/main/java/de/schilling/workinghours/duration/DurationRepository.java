package de.schilling.workinghours.duration;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface DurationRepository extends JpaRepository<Duration, Long>{

    List<Duration> findByUsernameAndStartTimeBetweenOrderByStartTimeDesc(String username, LocalDateTime start, LocalDateTime end);

    List<Duration> findByStartTimeBetweenOrderByStartTimeDesc(LocalDateTime start, LocalDateTime end);
}
