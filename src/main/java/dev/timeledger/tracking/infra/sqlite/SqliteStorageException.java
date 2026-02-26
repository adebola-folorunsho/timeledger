package dev.timeledger.tracking.infra.sqlite;

public final class SqliteStorageException extends RuntimeException {
    public SqliteStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}