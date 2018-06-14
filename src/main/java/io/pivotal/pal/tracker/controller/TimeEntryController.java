package io.pivotal.pal.tracker.controller;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    private final CounterService counter;
    private final GaugeService gauge;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }


    @RequestMapping(path = "/time-entries", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeEntry);

    }

    @RequestMapping(path = "/time-entries/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = this.timeEntryRepository.find(id);

        if(null == timeEntry){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        counter.increment("TimeEntry.read");
        return ResponseEntity.ok(timeEntry);
    }

    @RequestMapping(path = "/time-entries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        List<TimeEntry> timeEntries = this.timeEntryRepository.list();

        return ResponseEntity.ok(timeEntries);
    }

    @RequestMapping(path = "/time-entries/{id}", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {

        TimeEntry updatedTimeEntry = this.timeEntryRepository.update(id, timeEntry);
        if(null == updatedTimeEntry){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        counter.increment("TimeEntry.updated");
        return ResponseEntity.ok(updatedTimeEntry);


    }

    @RequestMapping(path = "/time-entries/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        this.timeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
