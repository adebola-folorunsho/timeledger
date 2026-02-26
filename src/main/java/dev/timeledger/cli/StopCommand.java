package dev.timeledger.cli;

import picocli.CommandLine.Command;

@Command(
        name = "stop",
        description = "Stop the currently running session."
)
public final class StopCommand implements Runnable {

    private CommandContext ctx;

    @Override
    public void run() {
        var status = ctx.statusUseCase().status();

        if (status.active().isEmpty()) {
            ctx.out().println("No active session");
            return;
        }

        var session = status.active().orElseThrow();
        ctx.stopUseCase().stop();

        ctx.out().printf("Stopped: %s%n", session.projectName());
    }

    public void setContext(CommandContext ctx) {
        this.ctx = ctx;
    }
}