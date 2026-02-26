package dev.timeledger.tracking.usecase;

import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.model.ActiveSession;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

final class GetStatusUseCaseTest {

    @Test
    void returnsEmptyWhenNoSessionIsRunning() {
        var activeSessions = new InMemoryActiveSessionRepository();
        var useCase = new GetStatusUseCase(activeSessions);

        var status = useCase.status();

        assertThat(status.active()).isEmpty();
    }

    @Test
    void returnsActiveSessionWhenRunning() {
        var activeSessions = new InMemoryActiveSessionRepository();
        activeSessions.saveActive(new ActiveSession("project-alpha", Instant.parse("2026-02-25T10:00:00Z")));

        var useCase = new GetStatusUseCase(activeSessions);

        var status = useCase.status();

        assertThat(status.active()).isPresent();
        assertThat(status.active().orElseThrow().projectName()).isEqualTo("project-alpha");
    }
}