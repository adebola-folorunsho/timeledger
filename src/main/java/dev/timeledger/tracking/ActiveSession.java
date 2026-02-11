package dev.timeledger.tracking;

import java.time.Instant;
import java.util.Objects;

final class ActiveSession {
    private final String projectName;
    private final Instant startedAt;

    ActiveSession(String projectName, Instant startedAt) {
        this.projectName = requireNonBlank(projectName, "projectName");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt");
    }

    String projectName() {
        return projectName;
    }

    Instant startedAt() {
        return startedAt;
    }

    private static String requireNonBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
