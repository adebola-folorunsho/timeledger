package dev.timeledger.cli;

import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.usecase.GetStatusUseCase;
import dev.timeledger.tracking.infra.system.SystemClock;
import dev.timeledger.tracking.usecase.StartWorkUseCase;
import dev.timeledger.tracking.infra.memory.InMemoryTimeEntryRepository;
import dev.timeledger.tracking.usecase.StopWorkUseCase;

final class AppWiring {
    private final InMemoryActiveSessionRepository activeSessions = new InMemoryActiveSessionRepository();
    private final SystemClock clock = new SystemClock();
    private final InMemoryTimeEntryRepository timeEntries = new InMemoryTimeEntryRepository();

    GetStatusUseCase statusUseCase() {
        return new GetStatusUseCase(activeSessions);
    }

    public StartWorkUseCase startUseCase() {
        return new StartWorkUseCase(clock, activeSessions);
    }

    public StopWorkUseCase stopUseCase() {
        return new StopWorkUseCase(clock, activeSessions, timeEntries);
    }
}