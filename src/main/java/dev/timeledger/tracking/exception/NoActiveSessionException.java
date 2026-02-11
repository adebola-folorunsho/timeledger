package dev.timeledger.tracking.exception;

public final class NoActiveSessionException extends RuntimeException {
    public NoActiveSessionException(String message) {
        super(message);
    }
}
