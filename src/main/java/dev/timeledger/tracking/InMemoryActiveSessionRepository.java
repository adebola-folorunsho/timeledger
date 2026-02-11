package dev.timeledger.tracking;

import java.util.Optional;

final class InMemoryActiveSessionRepository implements ActiveSessionRepository {
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
