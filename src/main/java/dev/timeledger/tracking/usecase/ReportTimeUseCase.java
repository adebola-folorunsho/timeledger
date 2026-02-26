package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.model.TimeEntry;
import dev.timeledger.tracking.port.TimeEntryRepository;

import java.time.Duration;
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
}