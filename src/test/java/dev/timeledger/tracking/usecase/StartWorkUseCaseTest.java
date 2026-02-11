package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.exception.ActiveSessionAlreadyRunningException;
import dev.timeledger.tracking.infra.memory.FixedClock;
import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.model.ActiveSession;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;


final class StartWorkUseCaseTest {

    @Test
    void refusesToStartWhenAnotherProjectIsAlreadyRunning() {
        var clock = new FixedClock(Instant.parse("2026-02-11T16:00:00Z"));
        var activeSessionRepo = new InMemoryActiveSessionRepository();

        activeSessionRepo.saveActive(new ActiveSession("project-alpha", Instant.parse("2026-02-11T15:00:00Z")));

        var useCase = new StartWorkUseCase(clock, activeSessionRepo);

        assertThatThrownBy(() -> useCase.start("project-beta"))
                .isInstanceOf(ActiveSessionAlreadyRunningException.class);
    }

    @Test
    void startsAProjectAndStoresItAsActive() {
        var now = Instant.parse("2026-02-11T16:00:00Z");
        var clock = new FixedClock(now);
        var activeSessionRepo = new InMemoryActiveSessionRepository();

        var useCase = new StartWorkUseCase(clock, activeSessionRepo);

        useCase.start("project-alpha");

        var active = activeSessionRepo.findActive().orElseThrow();
        assertThat(active.projectName()).isEqualTo("project-alpha");
        assertThat(active.startedAt()).isEqualTo(now);
    }

}
