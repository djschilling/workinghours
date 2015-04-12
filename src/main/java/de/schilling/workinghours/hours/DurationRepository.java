package de.schilling.workinghours.hours;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface DurationRepository extends JpaRepository<Duration, Long>{

    List<Duration> findByUsername(String username);
}
