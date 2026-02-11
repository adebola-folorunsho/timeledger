package dev.timeledger.tracking.port;

import dev.timeledger.tracking.model.TimeEntry;

public interface TimeEntryRepository {
    void save(TimeEntry entry);
}
