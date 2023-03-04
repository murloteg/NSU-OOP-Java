package ru.nsu.bolotov.exceptions;

public class FailedFileWriteException extends RuntimeException {
    public FailedFileWriteException() {
        super();
    }

    public FailedFileWriteException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Failure writing to file";
    }
}
