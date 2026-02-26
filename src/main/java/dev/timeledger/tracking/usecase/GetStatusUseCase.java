package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.model.ActiveSession;
import dev.timeledger.tracking.port.ActiveSessionRepository;

import java.util.Objects;
import java.util.Optional;

public final class GetStatusUseCase {

    private final ActiveSessionRepository activeSessions;

    public GetStatusUseCase(ActiveSessionRepository activeSessions) {
        this.activeSessions = Objects.requireNonNull(activeSessions, "activeSessions");
    }

    public Status status() {
        return new Status(activeSessions.findActive());
    }

    public record Status(Optional<ActiveSession> active) {
        public Status {
            active = active == null ? Optional.empty() : active;
        }
    }
}