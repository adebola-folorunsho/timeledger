package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.infra.memory.InMemoryTimeEntryRepository;
import dev.timeledger.tracking.model.TimeEntry;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

final class ReportTimeUseCaseRangeTest {

    @Test
    void sumsOnlyEntriesWithinRange() {
        var repo = new InMemoryTimeEntryRepository();
        repo.save(new TimeEntry("project-alpha",
                Instant.parse("2026-02-01T00:00:00Z"),
                Instant.parse("2026-02-01T01:00:00Z"))); // inside
        repo.save(new TimeEntry("project-alpha",
                Instant.parse("2026-03-01T00:00:00Z"),
                Instant.parse("2026-03-01T01:00:00Z"))); // outside

        var useCase = new ReportTimeUseCase(repo);

        var since = Instant.parse("2026-02-01T00:00:00Z");
        var until = Instant.parse("2026-02-28T23:59:59Z");

        Duration total = useCase.totalForProjectBetween("project-alpha", since, until);

        assertThat(total).isEqualTo(Duration.ofMinutes(60));
    }
}