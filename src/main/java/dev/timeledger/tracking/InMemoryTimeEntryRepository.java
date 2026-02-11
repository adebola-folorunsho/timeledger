package dev.timeledger.tracking;

import java.util.ArrayList;
import java.util.List;

final class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private final List<TimeEntry> saved = new ArrayList<>();

    @Override
    public void save(TimeEntry entry) {
        saved.add(entry);
    }

    List<TimeEntry> all() {
        return List.copyOf(saved);
    }
}
