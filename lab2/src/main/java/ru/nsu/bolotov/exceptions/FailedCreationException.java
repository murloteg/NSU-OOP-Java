package ru.nsu.bolotov.exceptions;

public class FailedCreationException extends RuntimeException {
    public FailedCreationException() {
        super();
    }

    public FailedCreationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Failed creation in factory";
    }
}
