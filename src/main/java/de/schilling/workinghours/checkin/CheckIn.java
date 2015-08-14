package de.schilling.workinghours.checkin;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * David Schilling - davejs92@gmail.com
 **/
@Entity
public class CheckIn {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private String username;

    public CheckIn(LocalDateTime startTime, String username) {
        this.startTime = startTime;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getUsername() {
        return username;
    }
}
