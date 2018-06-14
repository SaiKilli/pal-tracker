package io.pivotal.pal.tracker.controllers;

import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public Health health() {
        if(timeEntryRepository.list().size() < 5){
            return Health.up().build();
        }
        return Health.down().build();
    }
}
