package dev.timeledger.cli;

import dev.timeledger.tracking.port.Clock;

import java.time.Instant;
import java.util.Objects;

final class TickingClock implements Clock {

    private Instant now;

    TickingClock(String isoInstant) {
        this(Instant.parse(isoInstant));
    }

    TickingClock(Instant initial) {
        this.now = Objects.requireNonNull(initial, "initial");
    }

    @Override
    public Instant now() {
        return now;
    }

    void advanceMinutes(long minutes) {
        now = now.plusSeconds(minutes * 60);
    }
}