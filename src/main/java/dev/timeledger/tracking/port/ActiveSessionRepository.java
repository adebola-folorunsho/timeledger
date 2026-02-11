package dev.timeledger.tracking.port;

import dev.timeledger.tracking.model.ActiveSession;

import java.util.Optional;

public interface ActiveSessionRepository {
    Optional<ActiveSession> findActive();
    void saveActive(ActiveSession session);
    void clearActive();
}
