package dev.timeledger.cli;

import picocli.CommandLine.Command;

@Command(
        name = "timeledger",
        mixinStandardHelpOptions = true,
        description = "Track freelance work sessions and generate reports.",
        subcommands = { StatusCommand.class, StartCommand.class, StopCommand.class }
)
public final class RootCommand implements Runnable {

    @Override
    public void run() {
        // No-op: user should run a subcommand or -h for help.
    }
}