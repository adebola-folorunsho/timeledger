package dev.timeledger.cli;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

final class ReportCommandValidationTest {

    @Test
    void failsWhenOnlySinceIsProvided() throws Exception {
        var clock = new TickingClock("2026-02-01T00:00:00Z");
        var dbFile = Files.createTempFile("timeledger-cli-validate-test-", ".db");
        var wiring = new AppWiring(clock, () -> dbFile);

        var result = TimeledgerApp.runWith(
                wiring,
                "report", "project-alpha",
                "--since", "2026-02-01T00:00:00Z"
        );

        assertThat(result.exitCode()).isNotEqualTo(0);
        assertThat(result.output()).contains("Both --since and --until must be provided together.");
    }
}