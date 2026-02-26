package dev.timeledger.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
        name = "report",
        description = "Report total tracked time for a project."
)
public final class ReportCommand implements Runnable {

    private CommandContext ctx;

    @Parameters(index = "0", paramLabel = "<project>", description = "Project name to report on.")
    private String project;

    @Override
    public void run() {
        var total = ctx.reportUseCase().totalForProject(project);
        long minutes = total.toMinutes();
        ctx.out().printf("Total: %dm%n", minutes);
    }

    public void setContext(CommandContext ctx) {
        this.ctx = ctx;
    }
}