package dev.timeledger.tracking;

import java.util.Objects;

final class StartWorkUseCase {
    private final Clock clock;
    private final ActiveSessionRepository activeSessions;

    StartWorkUseCase(Clock clock, ActiveSessionRepository activeSessions) {
        this.clock = Objects.requireNonNull(clock, "clock");
        this.activeSessions = Objects.requireNonNull(activeSessions, "activeSessions");
    }

    void start(String projectName) {
        if (activeSessions.findActive().isPresent()) {
            throw new ActiveSessionAlreadyRunningException("Another project is already running");
        }
        activeSessions.saveActive(new ActiveSession(projectName, clock.now()));
    }
}
