package de.schilling.workinghours.checkin;

import java.time.LocalDateTime;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class CheckOut {

    private String username;
    private LocalDateTime endTime;

    public CheckOut(String username, LocalDateTime endTime) {
        this.username = username;
        this.endTime = endTime;
    }

    public CheckOut() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
