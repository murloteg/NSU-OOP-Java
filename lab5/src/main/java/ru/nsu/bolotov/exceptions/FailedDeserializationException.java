package ru.nsu.bolotov.exceptions;

public class FailedDeserializationException extends RuntimeException {
    private final String message;

    public FailedDeserializationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.format("Cannot deserialize object because: %s", message);
    }
}
