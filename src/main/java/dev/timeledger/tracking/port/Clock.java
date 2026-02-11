package dev.timeledger.tracking.port;

import java.time.Instant;

public interface Clock {
    Instant now();
}
