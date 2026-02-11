package dev.timeledger.tracking;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

final class StartWorkUseCaseTest {

    @Test
    void refusesToStartWhenAnotherProjectIsAlreadyRunning() {
        var clock = new FixedClock(Instant.parse("2026-02-11T16:00:00Z"));
        var activeSessionRepo = new InMemoryActiveSessionRepository();

        activeSessionRepo.setActive(new ActiveSession("project-alpha", Instant.parse("2026-02-11T15:00:00Z")));

        var useCase = new StartWorkUseCase(clock, activeSessionRepo);

        assertThatThrownBy(() -> useCase.start("project-beta"))
                .isInstanceOf(ActiveSessionAlreadyRunningException.class);
    }
}
