package ru.nsu.bolotov.exceptions;

public class FailedFileReadException extends RuntimeException {
    public FailedFileReadException() {
        super();
    }

    public FailedFileReadException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Failed reading from file";
    }
}
