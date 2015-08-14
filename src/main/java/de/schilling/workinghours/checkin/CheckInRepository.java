package de.schilling.workinghours.checkin;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    CheckIn findByUsername(String username);
}
