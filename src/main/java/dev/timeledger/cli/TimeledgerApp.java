package dev.timeledger.cli;

import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class TimeledgerApp {

    private TimeledgerApp() {}

    public static void main(String[] args) {
        var result = run(args);
        System.out.print(result.output());
        System.exit(result.exitCode());
    }

    static RunResult run(String... args) {
        var out = new StringWriter();
        var err = new StringWriter();

        var cmd = new CommandLine(new RootCommand())
                .setOut(new PrintWriter(out))
                .setErr(new PrintWriter(err));

        int exitCode = cmd.execute(args);
        return new RunResult(exitCode, out.toString() + err.toString());
    }

    public record RunResult(int exitCode, String output) {}
}
