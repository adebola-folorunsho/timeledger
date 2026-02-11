package dev.timeledger.tracking;

import java.time.Instant;
import java.util.Objects;

final class FixedClock implements Clock {
    private final Instant fixedNow;

    FixedClock(Instant fixedNow) {
        this.fixedNow = Objects.requireNonNull(fixedNow, "fixedNow");
    }

    @Override
    public Instant now() {
        return fixedNow;
    }
}
