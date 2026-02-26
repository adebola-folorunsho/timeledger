package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class ReportCommandTest {

    @Test
    void reportsTotalMinutesForAProject() {
        var wiring = new AppWiring();

        TimeledgerApp.runWith(wiring, "start", "project-alpha");
        TimeledgerApp.runWith(wiring, "stop");

        TimeledgerApp.runWith(wiring, "start", "project-alpha");
        TimeledgerApp.runWith(wiring, "stop");

        var report = TimeledgerApp.runWith(wiring, "report", "project-alpha");

        assertThat(report.exitCode()).isEqualTo(0);
        assertThat(report.output()).contains("Total: 0m");    }
}