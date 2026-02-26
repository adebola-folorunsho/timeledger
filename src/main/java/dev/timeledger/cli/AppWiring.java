package dev.timeledger.cli;

import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.usecase.GetStatusUseCase;
import dev.timeledger.tracking.infra.system.SystemClock;
import dev.timeledger.tracking.usecase.StartWorkUseCase;

final class AppWiring {
    private final InMemoryActiveSessionRepository activeSessions = new InMemoryActiveSessionRepository();
    private final SystemClock clock = new SystemClock();

    GetStatusUseCase statusUseCase() {
        return new GetStatusUseCase(activeSessions);
    }

    public StartWorkUseCase startUseCase() {
        return new StartWorkUseCase(clock, activeSessions);
    }
}