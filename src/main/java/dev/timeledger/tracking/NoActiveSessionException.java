package dev.timeledger.tracking;

final class NoActiveSessionException extends RuntimeException {
    NoActiveSessionException(String message) {
        super(message);
    }
}
