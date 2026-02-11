package dev.timeledger.tracking.infra.memory;

import dev.timeledger.tracking.model.TimeEntry;
import dev.timeledger.tracking.port.TimeEntryRepository;

import java.util.ArrayList;
import java.util.List;

public final class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private final List<TimeEntry> saved = new ArrayList<>();

    @Override
    public void save(TimeEntry entry) {
        saved.add(entry);
    }

    public List<TimeEntry> all() {
        return List.copyOf(saved);
    }
}
