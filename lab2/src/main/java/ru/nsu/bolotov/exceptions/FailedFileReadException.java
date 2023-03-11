package ru.nsu.bolotov.exceptions;

public class FailedFileReadException extends RuntimeException {
    public FailedFileReadException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Failed reading from file";
    }
}
