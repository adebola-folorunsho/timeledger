package dev.timeledger.tracking.infra.system;

import dev.timeledger.tracking.port.Clock;

import java.time.Instant;

public final class SystemClock implements Clock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}