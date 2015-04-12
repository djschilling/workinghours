package de.schilling.workinghours.hours;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * David Schilling - davejs92@gmail.com
 */
@Service
public class DurationService {

    private DurationRepository durationRepository;

    @Autowired
    public DurationService(DurationRepository durationRepository) {
        this.durationRepository = durationRepository;
    }

    public Duration save(Duration duration) {
        return durationRepository.save(duration);
    }

    public List<Duration> getAll() {
        return durationRepository.findAll();
    }
}
