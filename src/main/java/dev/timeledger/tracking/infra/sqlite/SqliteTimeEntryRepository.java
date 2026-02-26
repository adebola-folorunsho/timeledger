package dev.timeledger.tracking.infra.sqlite;

import dev.timeledger.tracking.model.TimeEntry;
import dev.timeledger.tracking.port.TimeEntryRepository;

import java.nio.file.Path;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SqliteTimeEntryRepository implements TimeEntryRepository {

    private final Path dbFile;

    public SqliteTimeEntryRepository(Path dbFile) {
        this.dbFile = Objects.requireNonNull(dbFile, "dbFile");
        initializeSchema();
    }

    @Override
    public void save(TimeEntry entry) {
        Objects.requireNonNull(entry, "entry");
        try (var conn = openConnection();
             var ps = conn.prepareStatement(
                     "INSERT INTO time_entries (project_name, started_at, stopped_at) VALUES (?, ?, ?)"
             )) {

            ps.setString(1, entry.projectName());
            ps.setString(2, entry.startedAt().toString());
            ps.setString(3, entry.stoppedAt().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to save time entry", e);
        }
    }

    @Override
    public List<TimeEntry> all() {
        try (var conn = openConnection();
             var ps = conn.prepareStatement(
                     "SELECT project_name, started_at, stopped_at FROM time_entries ORDER BY rowid ASC"
             );
             var rs = ps.executeQuery()) {

            var results = new ArrayList<TimeEntry>();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return List.copyOf(results);

        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to load time entries", e);
        }
    }

    private void initializeSchema() {
        try (var conn = openConnection();
             var st = conn.createStatement()) {

            st.execute("""
                    CREATE TABLE IF NOT EXISTS time_entries (
                      project_name TEXT NOT NULL,
                      started_at   TEXT NOT NULL,
                      stopped_at   TEXT NOT NULL
                    )
                    """);

        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to initialize schema", e);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString());
    }

    private TimeEntry mapRow(ResultSet rs) throws SQLException {
        String project = rs.getString("project_name");
        Instant startedAt = Instant.parse(rs.getString("started_at"));
        Instant stoppedAt = Instant.parse(rs.getString("stopped_at"));
        return new TimeEntry(project, startedAt, stoppedAt);
    }
}