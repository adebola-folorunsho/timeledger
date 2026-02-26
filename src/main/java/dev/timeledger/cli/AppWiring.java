package dev.timeledger.cli;

import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.usecase.GetStatusUseCase;
import dev.timeledger.tracking.infra.system.SystemClock;
import dev.timeledger.tracking.usecase.StartWorkUseCase;
import dev.timeledger.tracking.infra.memory.InMemoryTimeEntryRepository;
import dev.timeledger.tracking.usecase.StopWorkUseCase;
import dev.timeledger.tracking.usecase.ReportTimeUseCase;
import dev.timeledger.tracking.port.Clock;

final class AppWiring {
    private final InMemoryActiveSessionRepository activeSessions = new InMemoryActiveSessionRepository();
    private final Clock clock;
    private final InMemoryTimeEntryRepository timeEntries = new InMemoryTimeEntryRepository();

    public AppWiring() {
        this(new dev.timeledger.tracking.infra.system.SystemClock());
    }

    public AppWiring(Clock clock) {
        this.clock = java.util.Objects.requireNonNull(clock, "clock");
    }

    GetStatusUseCase statusUseCase() {
        return new GetStatusUseCase(activeSessions);
    }

    public StartWorkUseCase startUseCase() {
        return new StartWorkUseCase(clock, activeSessions);
    }

    public StopWorkUseCase stopUseCase() {
        return new StopWorkUseCase(clock, activeSessions, timeEntries);
    }

    public ReportTimeUseCase reportUseCase() {
        return new ReportTimeUseCase(timeEntries);
    }
}