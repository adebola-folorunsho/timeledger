package dev.timeledger.cli;

import dev.timeledger.tracking.infra.memory.InMemoryActiveSessionRepository;
import dev.timeledger.tracking.usecase.GetStatusUseCase;
import dev.timeledger.tracking.usecase.StartWorkUseCase;
import dev.timeledger.tracking.usecase.StopWorkUseCase;
import dev.timeledger.tracking.usecase.ReportTimeUseCase;
import dev.timeledger.tracking.port.Clock;
import dev.timeledger.tracking.infra.sqlite.SqliteTimeEntryRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

final class AppWiring {
    private final InMemoryActiveSessionRepository activeSessions = new InMemoryActiveSessionRepository();
    private final Clock clock;
    private final SqliteTimeEntryRepository timeEntries;
    private final DbPath dbPath;

    public AppWiring() {
        this(new dev.timeledger.tracking.infra.system.SystemClock(), TimeledgerPaths.defaultDbPath());
    }

    public AppWiring(Clock clock) {
        this(clock, TimeledgerPaths.defaultDbPath());
    }

    public AppWiring(Clock clock, DbPath dbPath) {
        this.clock = Objects.requireNonNull(clock, "clock");
        this.dbPath = Objects.requireNonNull(dbPath, "dbPath");

        Path dbFile = this.dbPath.dbFile();
        ensureParentDirectoryExists(dbFile);
        this.timeEntries = new SqliteTimeEntryRepository(dbFile);
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

    private static void ensureParentDirectoryExists(Path dbFile) {
        try {
            Files.createDirectories(dbFile.getParent());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create data directory: " + dbFile.getParent(), e);
        }
    }
}