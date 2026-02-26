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
        var buffers = OutputBuffers.create();
        var cmd = buildCommandLine(buffers.outWriter(), buffers.errWriter());
        int exitCode = cmd.execute(args);
        return new RunResult(exitCode, buffers.combinedOutput());
    }

    private static CommandLine buildCommandLine(PrintWriter out, PrintWriter err) {
        var wiring = new AppWiring();
        var root = new RootCommand();
        var cmd = new CommandLine(root).setOut(out).setErr(err);

        injectContext(cmd, new CommandContext(out, wiring));
        return cmd;
    }

    private static void injectContext(CommandLine cmd, CommandContext ctx) {
        var status = cmd.getSubcommands().get("status").getCommand();
        if (status instanceof StatusCommand sc) {
            sc.setContext(ctx);
        }
    }

    private record OutputBuffers(StringWriter outBuffer, StringWriter errBuffer) {

        static OutputBuffers create() {
            return new OutputBuffers(new StringWriter(), new StringWriter());
        }

        PrintWriter outWriter() {
            return new PrintWriter(outBuffer);
        }

        PrintWriter errWriter() {
            return new PrintWriter(errBuffer);
        }

        String combinedOutput() {
            return outBuffer.toString() + errBuffer.toString();
        }
    }

    public record RunResult(int exitCode, String output) {}
}