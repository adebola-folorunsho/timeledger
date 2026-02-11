package dev.timeledger.tracking.infra.memory;

import dev.timeledger.tracking.model.ActiveSession;
import dev.timeledger.tracking.port.ActiveSessionRepository;

import java.util.Optional;

public final class InMemoryActiveSessionRepository implements ActiveSessionRepository {
    private ActiveSession active;

    @Override
    public Optional<ActiveSession> findActive() {
        return Optional.ofNullable(active);
    }

    @Override
    public void saveActive(ActiveSession session) {
        this.active = session;
    }

    @Override
    public void clearActive() {
        this.active = null;
    }

}
