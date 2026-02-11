package dev.timeledger.tracking.model;

import java.time.Instant;
import java.util.Objects;

public final class ActiveSession {
    private final String projectName;
    private final Instant startedAt;

    public ActiveSession(String projectName, Instant startedAt) {
        this.projectName = requireNonBlank(projectName, "projectName");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt");
    }

    public String projectName() {
        return projectName;
    }

    public Instant startedAt() {
        return startedAt;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
