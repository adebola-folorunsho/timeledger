package dev.timeledger.tracking;

final class ActiveSessionAlreadyRunningException extends RuntimeException {
    ActiveSessionAlreadyRunningException(String message) {
        super(message);
    }
}
