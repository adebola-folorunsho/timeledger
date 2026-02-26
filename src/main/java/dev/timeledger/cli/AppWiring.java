package dev.timeledger.cli;

import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.usecase.GetStatusUseCase;

final class AppWiring {
    private final InMemoryActiveSessionRepository activeSessions = new InMemoryActiveSessionRepository();

    GetStatusUseCase statusUseCase() {
        return new GetStatusUseCase(activeSessions);
    }
}