package dev.timeledger.cli;

import java.nio.file.Path;

final class TimeledgerPaths {

    private TimeledgerPaths() {}

    static Path defaultDbFile() {
        String home = System.getProperty("user.home");
        return Path.of(home, ".timeledger", "timeledger.db");
    }

    static DbPath defaultDbPath() {
        return TimeledgerPaths::defaultDbFile;
    }
}