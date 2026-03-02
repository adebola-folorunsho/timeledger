package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.model.TimeEntry;
import dev.timeledger.tracking.port.TimeEntryRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public final class ReportTimeUseCase {

    private final TimeEntryRepository repo;

    public ReportTimeUseCase(TimeEntryRepository repo) {
        this.repo = Objects.requireNonNull(repo, "repo");
    }

    public Duration totalForProject(String projectName) {
        return repo.all().stream()
                .filter(e -> e.projectName().equals(projectName))
                .map(this::durationOf)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Duration durationOf(TimeEntry entry) {
        return Duration.between(entry.startedAt(), entry.stoppedAt());
    }

    public Duration totalForProjectBetween(String projectName, Instant since, Instant until) {
        Objects.requireNonNull(projectName, "projectName");
        Objects.requireNonNull(since, "since");
        Objects.requireNonNull(until, "until");

        return repo.all().stream()
                .filter(e -> e.projectName().equals(projectName))
                .filter(e -> !e.startedAt().isBefore(since))
                .filter(e -> !e.stoppedAt().isAfter(until))
                .map(this::durationOf)
                .reduce(Duration.ZERO, Duration::plus);
    }
}