package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

final class StatusCommandTest {

    @Test
    void printsNoActiveSessionWhenNothingIsRunning() throws Exception {
        var dbFile = Files.createTempFile("timeledger-status-test-", ".db");
        var wiring = new AppWiring(new TickingClock("2026-02-26T00:00:00Z"), () -> dbFile);

        var result = TimeledgerApp.runWith(wiring, "status");

        assertThat(result.exitCode()).isEqualTo(0);
        assertThat(result.output()).contains("No active session");
    }
}