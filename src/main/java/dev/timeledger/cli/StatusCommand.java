package dev.timeledger.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(
        name = "status",
        description = "Show the currently running session, if any."
)
public final class StatusCommand implements Runnable {

    @ParentCommand
    private RootCommand root; // required by picocli, even if we don’t call it

    private CommandContext ctx;

    void setContext(CommandContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        var status = ctx.statusUseCase().status();

        if (status.active().isEmpty()) {
            ctx.out().println("No active session");
            return;
        }

        var session = status.active().orElseThrow();
        ctx.out().printf("Running: %s (since %s)%n", session.projectName(), session.startedAt());
    }
}