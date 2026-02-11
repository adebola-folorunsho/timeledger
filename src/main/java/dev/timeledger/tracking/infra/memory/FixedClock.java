package dev.timeledger.tracking.infra.memory;

import dev.timeledger.tracking.port.Clock;

import java.time.Instant;
import java.util.Objects;

public final class FixedClock implements Clock {
    private final Instant fixedNow;

    public FixedClock(Instant fixedNow) {
        this.fixedNow = Objects.requireNonNull(fixedNow, "fixedNow");
    }

    @Override
    public Instant now() {
        return fixedNow;
    }
}
