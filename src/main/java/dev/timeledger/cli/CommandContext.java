package dev.timeledger.cli;

import dev.timeledger.tracking.usecase.GetStatusUseCase;
import dev.timeledger.tracking.usecase.StartWorkUseCase;

import java.io.PrintWriter;
import java.util.Objects;

final class CommandContext {
    private final PrintWriter out;
    private final AppWiring wiring;

    CommandContext(PrintWriter out, AppWiring wiring) {
        this.out = Objects.requireNonNull(out, "out");
        this.wiring = Objects.requireNonNull(wiring, "wiring");
    }

    PrintWriter out() {
        return out;
    }

    GetStatusUseCase statusUseCase() {
        return wiring.statusUseCase();
    }

    public StartWorkUseCase startUseCase() {
        return wiring.startUseCase();
    }
}