package dev.timeledger.tracking.infra.sqlite;

import dev.timeledger.tracking.model.TimeEntry;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

final class SqliteTimeEntryRepositoryTest {

    @Test
    void savesAndLoadsEntriesFromSqlite() throws Exception {
        var dbFile = Files.createTempFile("timeledger-test-", ".db");

        var repo = new SqliteTimeEntryRepository(dbFile);

        repo.save(new TimeEntry(
                "project-alpha",
                Instant.parse("2026-02-26T00:00:00Z"),
                Instant.parse("2026-02-26T01:00:00Z")
        ));

        var all = repo.all();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).projectName()).isEqualTo("project-alpha");
    }
}