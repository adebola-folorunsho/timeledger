package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.infra.memory.InMemoryTimeEntryRepository;
import dev.timeledger.tracking.model.TimeEntry;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

final class ReportTimeUseCaseTest {

    @Test
    void sumsDurationsForAProject() {
        var repo = new InMemoryTimeEntryRepository();
        repo.save(new TimeEntry("project-alpha",
                Instant.parse("2026-02-26T00:00:00Z"),
                Instant.parse("2026-02-26T01:00:00Z")));
        repo.save(new TimeEntry("project-alpha",
                Instant.parse("2026-02-26T02:00:00Z"),
                Instant.parse("2026-02-26T02:30:00Z")));
        repo.save(new TimeEntry("project-beta",
                Instant.parse("2026-02-26T03:00:00Z"),
                Instant.parse("2026-02-26T04:00:00Z")));

        var useCase = new ReportTimeUseCase(repo);

        Duration total = useCase.totalForProject("project-alpha");

        assertThat(total).isEqualTo(Duration.ofMinutes(90));
    }
}