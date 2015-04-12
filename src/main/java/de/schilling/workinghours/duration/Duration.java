package de.schilling.workinghours.duration;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * David Schilling - davejs92@gmail.com
 */
@Entity
public class Duration {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String username;

    public Duration() {
    }

    public Duration(LocalDateTime startTime, LocalDateTime endTime, String username) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;

    }
    public long getDuration() {
        if (startTime != null && endTime != null) {
            return java.time.Duration.between(startTime, endTime).toMinutes();
        }
        return 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
