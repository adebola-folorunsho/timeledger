package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class ReportCommandTest {

    @Test
    void reportsTotalMinutesForAProject() {
        var clock = new TickingClock("2026-02-26T00:00:00Z");
        var wiring = new AppWiring(clock);

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