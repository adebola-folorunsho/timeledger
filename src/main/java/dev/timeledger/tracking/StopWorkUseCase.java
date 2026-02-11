package dev.timeledger.tracking;

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

    void stop() {
        var active = activeSessions.findActive()
                .orElseThrow(() -> new NoActiveSessionException("No active session to stop"));

        timeEntries.save(new TimeEntry(active.projectName(), active.startedAt(), clock.now()));
        activeSessions.clearActive();
    }
}
