package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class TimeledgerAppTest {

    @Test
    void printsUsageWhenHelpIsRequested() {
        var result = TimeledgerApp.run("-h");
        assertThat(result.exitCode()).isEqualTo(0);
        assertThat(result.output()).contains("Usage:");
    }
}
