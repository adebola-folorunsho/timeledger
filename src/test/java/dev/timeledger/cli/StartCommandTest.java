package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class StartCommandTest {

    @Test
    void startsAProjectAndStatusShowsItAsRunning() {
        var wiring = new AppWiring();

        var start = TimeledgerApp.runWith(wiring, "start", "project-alpha");
        assertThat(start.exitCode()).isEqualTo(0);
        assertThat(start.output()).contains("Started: project-alpha");

        var status = TimeledgerApp.runWith(wiring, "status");
        assertThat(status.exitCode()).isEqualTo(0);
        assertThat(status.output()).contains("Running: project-alpha");
    }
}