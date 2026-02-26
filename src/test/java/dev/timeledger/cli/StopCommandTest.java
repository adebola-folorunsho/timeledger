package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class StopCommandTest {

    @Test
    void stopsTheActiveSessionAndStatusShowsNoActiveSession() {
        var wiring = new AppWiring();

        TimeledgerApp.runWith(wiring, "start", "project-alpha");

        var stop = TimeledgerApp.runWith(wiring, "stop");
        assertThat(stop.exitCode()).isEqualTo(0);
        assertThat(stop.output()).contains("Stopped: project-alpha");

        var status = TimeledgerApp.runWith(wiring, "status");
        assertThat(status.exitCode()).isEqualTo(0);
        assertThat(status.output()).contains("No active session");
    }
}