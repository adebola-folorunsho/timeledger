package dev.timeledger.tracking;

import java.util.Optional;

interface ActiveSessionRepository {
    Optional<ActiveSession> findActive();
    void saveActive(ActiveSession session);
    void clearActive();
}
