package de.schilling.workinghours.duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * David Schilling - davejs92@gmail.com
 */
@RestController
public class DurationController {

    private DurationService durationService;

    @Autowired
    public DurationController(DurationService durationService) {
        this.durationService = durationService;
    }

    @RequestMapping(value = "durations", method = RequestMethod.POST)
    public Duration save(@RequestBody @Valid Duration duration) {
        return durationService.create(duration);
    }

    @RequestMapping(value = "durations/{id}", method = RequestMethod.PUT)
    public Duration save(@RequestBody @Valid Duration duration, @PathVariable Long id) {
        duration.setId(id);
        return durationService.update(duration, id);
    }

    @RequestMapping(value = "durations", method = RequestMethod.GET)
    public List<Duration> getAll(@RequestParam(required = false) Integer year,
                                 @RequestParam(required = false) Integer month) {

        return durationService.get(year, month);
    }

    @RequestMapping(value = "durations", method = RequestMethod.GET, params = {"username"})
    public List<Duration> getAllForUsername(@RequestParam(value = "username") String username,
                                            @RequestParam(required = false) Integer year,
                                            @RequestParam(required = false) Integer month) {

        return durationService.get(username, year, month);
    }

    @RequestMapping(value = "durations/sum", method = RequestMethod.GET)
    public Long getSum(@RequestParam(required = false) Integer year,
                       @RequestParam(required = false) Integer month) {

        return durationService.calculateDurationSum(durationService.get(year, month));
    }

    @RequestMapping(value = "durations/sum", method = RequestMethod.GET, params = {"username"})
    public Long getSumForUsername(@RequestParam(value = "username") String username,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer month) {

        return durationService.calculateDurationSum(durationService.get(username, year, month));
    }

    @RequestMapping(value = "durations/{id}", method = RequestMethod.GET)
    public Duration get(@PathVariable Long id) {
        return durationService.get(id);
    }

    @RequestMapping(value = "durations/{id}", method = DELETE)
    public void delete(@PathVariable Long id) {
        durationService.delete(id);
    }

    @ExceptionHandler(InvalidDurationException.class)
    ResponseEntity<String> handleValidationException(InvalidDurationException e) {

        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

}
