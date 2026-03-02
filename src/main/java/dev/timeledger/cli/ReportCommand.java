package dev.timeledger.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import java.time.Instant;
import java.util.Optional;

@Command(
        name = "report",
        description = "Report total tracked time for a project."
)
public final class ReportCommand implements Runnable {

    private CommandContext ctx;

    @Parameters(index = "0", paramLabel = "<project>", description = "Project name to report on.")
    private String project;

    @Option(names = "--since", paramLabel = "<iso>", description = "Include entries starting from this instant (ISO-8601).")
    private String since;

    @Option(names = "--until", paramLabel = "<iso>", description = "Include entries up to this instant (ISO-8601).")
    private String until;

    @Override
    public void run() {
        var useCase = ctx.reportUseCase();

        var range = parseRange();
        var total = range.isPresent()
                ? useCase.totalForProjectBetween(project, range.get().since(), range.get().until())
                : useCase.totalForProject(project);

        ctx.out().printf("Total: %dm%n", total.toMinutes());
    }

    private Optional<Range> parseRange() {
        if (since == null && until == null) {
            return Optional.empty();
        }
        if (since == null || until == null) {
            throw new IllegalArgumentException("Both --since and --until must be provided together.");
        }
        return Optional.of(new Range(Instant.parse(since), Instant.parse(until)));
    }

    private record Range(Instant since, Instant until) {}

    public void setContext(CommandContext ctx) {
        this.ctx = ctx;
    }
}