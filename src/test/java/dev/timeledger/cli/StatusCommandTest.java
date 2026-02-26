package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class StatusCommandTest {

    @Test
    void printsNoActiveSessionWhenNothingIsRunning() {
        var result = TimeledgerApp.run("status");
        assertThat(result.exitCode()).isEqualTo(0);
        assertThat(result.output()).contains("No active session");
    }
}