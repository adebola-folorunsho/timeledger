package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.exception.NoActiveSessionException;
import dev.timeledger.tracking.model.TimeEntry;
import dev.timeledger.tracking.port.ActiveSessionRepository;
import dev.timeledger.tracking.port.Clock;
import dev.timeledger.tracking.port.TimeEntryRepository;


import java.util.Objects;

final class StopWorkUseCase {
    private final Clock clock;
    private final ActiveSessionRepository activeSessions;
    private final TimeEntryRepository timeEntries;

    StopWorkUseCase(Clock clock, ActiveSessionRepository activeSessions, TimeEntryRepository timeEntries) {
        this.clock = Objects.requireNonNull(clock, "clock");
        this.activeSessions = Objects.requireNonNull(activeSessions, "activeSessions");
        this.timeEntries = Objects.requireNonNull(timeEntries, "timeEntries");
    }

    public void stop() {
        var active = activeSessions.findActive()
                .orElseThrow(() -> new NoActiveSessionException("No active session to stop"));

        timeEntries.save(new TimeEntry(active.projectName(), active.startedAt(), clock.now()));
        activeSessions.clearActive();
    }
}
