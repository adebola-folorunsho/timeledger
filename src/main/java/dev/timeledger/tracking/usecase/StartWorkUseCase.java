package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.exception.ActiveSessionAlreadyRunningException;
import dev.timeledger.tracking.model.ActiveSession;
import dev.timeledger.tracking.port.ActiveSessionRepository;
import dev.timeledger.tracking.port.Clock;


import java.util.Objects;

public final class StartWorkUseCase {
    private final Clock clock;
    private final ActiveSessionRepository activeSessions;

    public StartWorkUseCase(Clock clock, ActiveSessionRepository activeSessions) {
        this.clock = Objects.requireNonNull(clock, "clock");
        this.activeSessions = Objects.requireNonNull(activeSessions, "activeSessions");
    }

    public void start(String projectName) {
        if (activeSessions.findActive().isPresent()) {
            throw new ActiveSessionAlreadyRunningException("Another project is already running");
        }
        activeSessions.saveActive(new ActiveSession(projectName, clock.now()));
    }
}
