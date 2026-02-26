package dev.timeledger.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
        name = "start",
        description = "Start tracking work on a project."
)
public final class StartCommand implements Runnable {

    @Parameters(index = "0", paramLabel = "<project>", description = "Project name to start tracking.")
    private String project;
    private CommandContext ctx;

    @Override
    public void run() {
        ctx.startUseCase().start(project);
        ctx.out().printf("Started: %s%n", project);
    }

    public void setContext(CommandContext ctx) {
        this.ctx = ctx;
    }
}