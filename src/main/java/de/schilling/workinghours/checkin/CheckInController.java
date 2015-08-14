package de.schilling.workinghours.checkin;

import de.schilling.workinghours.duration.InvalidDurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * David Schilling - davejs92@gmail.com
 **/
@RestController
public class CheckInController {

    private final CheckInService checkInService;

    @Autowired
    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }


    @RequestMapping(value = "checkins/{username}", method = GET)
    public CheckIn get(@PathVariable String username) {
        return checkInService.get(username);
    }

    @RequestMapping(value = "checkins", method = POST)
    public void checkIn(@RequestBody CheckIn checkIn) {
        checkInService.checkIn(checkIn);
    }

    @RequestMapping(value = "checkouts", method = POST)
    public void checkOut(@RequestBody CheckOut checkOut) {
        checkInService.checkOut(checkOut);
    }

    @ExceptionHandler(InvalidDurationException.class)
    ResponseEntity<String> handleValidationException(InvalidDurationException e) {

        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(CheckInServiceException.class)
    ResponseEntity<String> handleCheckinServiceException(CheckInServiceException e) {

        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

}
