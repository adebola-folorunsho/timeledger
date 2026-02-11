package dev.timeledger.cli;

import picocli.CommandLine.Command;

@Command(
        name = "timeledger",
        mixinStandardHelpOptions = true,
        description = "Track freelance work sessions and generate reports."
)
public final class RootCommand implements Runnable {

    @Override
    public void run() {
        // If no subcommand is provided, show usage via -h
    }
}
