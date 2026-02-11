package dev.timeledger.tracking.model;

import java.time.Instant;
import java.util.Objects;

public final class TimeEntry {
    private final String projectName;
    private final Instant startedAt;
    private final Instant stoppedAt;

    public TimeEntry(String projectName, Instant startedAt, Instant stoppedAt) {
        this.projectName = requireNonBlank(projectName, "projectName");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt");
        this.stoppedAt = Objects.requireNonNull(stoppedAt, "stoppedAt");

        if (stoppedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("stoppedAt must not be before startedAt");
        }
    }

    public String projectName() {
        return projectName;
    }

    public Instant startedAt() {
        return startedAt;
    }

    public Instant stoppedAt() {
        return stoppedAt;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
