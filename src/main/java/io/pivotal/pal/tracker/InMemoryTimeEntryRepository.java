package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private Map<Long, TimeEntry> inMemoryStore;

    private static Long id;

    public InMemoryTimeEntryRepository() {
        this.inMemoryStore = new HashMap<>();
        id = 0L;
    }

    public TimeEntry create(TimeEntry timeEntry){

        this.inMemoryStore.put(++id, timeEntry);
        timeEntry.setId(id);

        return timeEntry;
    }

    public TimeEntry find(long id) {

        return this.inMemoryStore.get(id);
    }

    public List<TimeEntry> list() {
        List<TimeEntry> timeEntries = new ArrayList<>();

        inMemoryStore.entrySet().forEach(timeEntry -> {
            timeEntries.add(timeEntry.getValue());
        });

        return timeEntries;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {

        if(!this.inMemoryStore.containsKey(id)){
            return null;
        }

        timeEntry.setId(id);
        this.inMemoryStore.put(id, timeEntry);
        return this.inMemoryStore.get(id);
    }

    public void delete(long id) {
        this.inMemoryStore.remove(id);
    }
}
