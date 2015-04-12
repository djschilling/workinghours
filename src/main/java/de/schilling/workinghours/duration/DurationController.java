package de.schilling.workinghours.duration;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return durationService.save(duration);
    }

    @RequestMapping(value = "durations/{id}", method = RequestMethod.PUT)
    public Duration save(@RequestBody @Valid Duration duration, @PathVariable Long id) {
        duration.setId(id);
        return durationService.save(duration);
    }

    @RequestMapping(value = "durations", method = RequestMethod.GET)
    public List<Duration> getAll() {
        return durationService.getAllForCurrentUser();
    }

    @RequestMapping(value = "durations", method = RequestMethod.GET, params = { "username"})
    public List<Duration> getAllForUsername(@RequestParam(value="username") String username) {
        return durationService.getAllForUser(username);
    }
}
