package dev.timeledger.tracking;

interface TimeEntryRepository {
    void save(TimeEntry entry);
}
