package de.schilling.workinghours.duration;

/**
 * Created by raphael on 11.08.15.
 */
public class InvalidDurationException extends RuntimeException {

    public InvalidDurationException(String message) {
        super(message);
    }
}
