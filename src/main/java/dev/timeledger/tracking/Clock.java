package dev.timeledger.tracking;

import java.time.Instant;

interface Clock {
    Instant now();
}
