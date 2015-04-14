package de.schilling.workinghours.duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<Duration> getAll(@RequestParam(value = "start", required = false) String start,
                                 @RequestParam(value = "end", required = false) String end) {
        List<Duration> list = durationService.getTimefilteredForCurrentUser(start, end);
        return list;
    }

    @RequestMapping(value = "durations", method = RequestMethod.GET, params = {"username"})
    public List<Duration> getAllForUsername(@RequestParam(value = "username") String username,
                                            @RequestParam(value = "start", required = false) String start,
                                            @RequestParam(value = "end", required = false) String end
    ) {
        return durationService.getTimefilteredForUser(username, start, end);
    }

    @RequestMapping(value = "durations/sum", method = RequestMethod.GET)
    public Long getSumForCurrentUser(@RequestParam(value = "start", required = false) String start,
                                     @RequestParam(value = "end", required = false) String end) {
        return durationService.calculateDurationSum(durationService.getTimefilteredForCurrentUser(start, end));
    }

    @RequestMapping(value = "durations/sum", method = RequestMethod.GET, params = {"username"})
    public Long getSumForUsername(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "start", required = false) String start,
                                  @RequestParam(value = "end", required = false) String end) {
        return durationService.calculateDurationSum(durationService.getTimefilteredForUser(username,start, end));
    }

}
