package dev.timeledger.tracking.port;

import dev.timeledger.tracking.model.TimeEntry;
import java.util.List;

public interface TimeEntryRepository {
    void save(TimeEntry entry);
    List<TimeEntry> all();
}
