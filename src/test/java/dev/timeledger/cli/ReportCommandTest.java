package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;

final class ReportCommandTest {

    @Test
    void reportsTotalMinutesForAProject() throws IOException {
        var clock = new TickingClock("2026-02-26T00:00:00Z");
        var dbFile = Files.createTempFile("timeledger-cli-test-", ".db");
        var wiring = new AppWiring(clock, () -> dbFile);

        TimeledgerApp.runWith(wiring, "start", "project-alpha");
        clock.advanceMinutes(60);
        TimeledgerApp.runWith(wiring, "stop");

        TimeledgerApp.runWith(wiring, "start", "project-alpha");
        clock.advanceMinutes(30);
        TimeledgerApp.runWith(wiring, "stop");

        var report = TimeledgerApp.runWith(wiring, "report", "project-alpha");

        assertThat(report.exitCode()).isEqualTo(0);
        assertThat(report.output()).contains("Total: 90m");
    }
}