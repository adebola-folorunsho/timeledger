package dev.timeledger.tracking.exception;

public final class ActiveSessionAlreadyRunningException extends RuntimeException {
    public ActiveSessionAlreadyRunningException(String message) {
        super(message);
    }
}
