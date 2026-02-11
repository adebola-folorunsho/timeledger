package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.exception.NoActiveSessionException;
import dev.timeledger.tracking.infra.memory.FixedClock;
import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.infra.memory.InMemoryTimeEntryRepository;
import dev.timeledger.tracking.model.ActiveSession;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;



final class StopWorkUseCaseTest {

    @Test
    void refusesToStopWhenNothingIsRunning() {
        var clock = new FixedClock(Instant.parse("2026-02-11T16:30:00Z"));
        var activeSessions = new InMemoryActiveSessionRepository();
        var timeEntries = new InMemoryTimeEntryRepository();

        var useCase = new StopWorkUseCase(clock, activeSessions, timeEntries);

        assertThatThrownBy(useCase::stop)
                .isInstanceOf(NoActiveSessionException.class);
    }

    @Test
    void stopsTheActiveSessionAndPersistsACompletedEntry() {
        var startedAt = Instant.parse("2026-02-11T16:00:00Z");
        var stoppedAt = Instant.parse("2026-02-11T16:30:00Z");

        var clock = new FixedClock(stoppedAt);
        var activeSessions = new InMemoryActiveSessionRepository();
        var timeEntries = new InMemoryTimeEntryRepository();

        activeSessions.saveActive(new ActiveSession("project-alpha", startedAt));

        var useCase = new StopWorkUseCase(clock, activeSessions, timeEntries);

        useCase.stop();

        assertThat(activeSessions.findActive()).isEmpty();
        assertThat(timeEntries.all()).hasSize(1);

        var entry = timeEntries.all().get(0);
        assertThat(entry.projectName()).isEqualTo("project-alpha");
        assertThat(entry.startedAt()).isEqualTo(startedAt);
        assertThat(entry.stoppedAt()).isEqualTo(stoppedAt);
    }

}
