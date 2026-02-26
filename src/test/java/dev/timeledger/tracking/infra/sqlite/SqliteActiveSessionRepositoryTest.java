package dev.timeledger.tracking.infra.sqlite;

import dev.timeledger.tracking.model.ActiveSession;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

final class SqliteActiveSessionRepositoryTest {

    @Test
    void savesAndLoadsActiveSession() throws Exception {
        var dbFile = Files.createTempFile("timeledger-active-test-", ".db");

        var repo = new SqliteActiveSessionRepository(dbFile);

        assertThat(repo.findActive()).isEmpty();

        repo.saveActive(new ActiveSession("project-alpha", Instant.parse("2026-02-26T00:00:00Z")));

        var loaded = repo.findActive().orElseThrow();
        assertThat(loaded.projectName()).isEqualTo("project-alpha");

        repo.clearActive();

        assertThat(repo.findActive()).isEmpty();
    }
}