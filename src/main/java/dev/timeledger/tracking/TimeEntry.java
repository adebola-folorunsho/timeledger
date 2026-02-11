package dev.timeledger.tracking;

import java.time.Instant;
import java.util.Objects;

final class TimeEntry {
    private final String projectName;
    private final Instant startedAt;
    private final Instant stoppedAt;

    TimeEntry(String projectName, Instant startedAt, Instant stoppedAt) {
        this.projectName = requireNonBlank(projectName, "projectName");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt");
        this.stoppedAt = Objects.requireNonNull(stoppedAt, "stoppedAt");

        if (stoppedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("stoppedAt must not be before startedAt");
        }
    }

    String projectName() {
        return projectName;
    }

    Instant startedAt() {
        return startedAt;
    }

    Instant stoppedAt() {
        return stoppedAt;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
