package dev.timeledger.tracking.infra.sqlite;

import dev.timeledger.tracking.model.ActiveSession;
import dev.timeledger.tracking.port.ActiveSessionRepository;

import java.nio.file.Path;
import java.sql.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public final class SqliteActiveSessionRepository implements ActiveSessionRepository {

    private final Path dbFile;

    public SqliteActiveSessionRepository(Path dbFile) {
        this.dbFile = Objects.requireNonNull(dbFile, "dbFile");
        initializeSchema();
    }

    @Override
    public Optional<ActiveSession> findActive() {
        try (var conn = openConnection();
             var ps = conn.prepareStatement("SELECT project_name, started_at FROM active_session LIMIT 1");
             var rs = ps.executeQuery()) {

            if (!rs.next()) {
                return Optional.empty();
            }

            String project = rs.getString("project_name");
            Instant startedAt = Instant.parse(rs.getString("started_at"));
            return Optional.of(new ActiveSession(project, startedAt));

        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to load active session", e);
        }
    }

    @Override
    public void saveActive(ActiveSession session) {
        Objects.requireNonNull(session, "session");

        try (var conn = openConnection()) {
            conn.setAutoCommit(false);

            try (var clear = conn.prepareStatement("DELETE FROM active_session")) {
                clear.executeUpdate();
            }

            try (var insert = conn.prepareStatement(
                    "INSERT INTO active_session (project_name, started_at) VALUES (?, ?)"
            )) {
                insert.setString(1, session.projectName());
                insert.setString(2, session.startedAt().toString());
                insert.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to save active session", e);
        }
    }

    @Override
    public void clearActive() {
        try (var conn = openConnection();
             var ps = conn.prepareStatement("DELETE FROM active_session")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to clear active session", e);
        }
    }

    private void initializeSchema() {
        try (var conn = openConnection();
             var st = conn.createStatement()) {

            st.execute("""
                    CREATE TABLE IF NOT EXISTS active_session (
                      project_name TEXT NOT NULL,
                      started_at   TEXT NOT NULL
                    )
                    """);

        } catch (SQLException e) {
            throw new SqliteStorageException("Failed to initialize schema", e);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString());
    }
}