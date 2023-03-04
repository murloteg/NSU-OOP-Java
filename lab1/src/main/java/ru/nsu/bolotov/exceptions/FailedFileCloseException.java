package ru.nsu.bolotov.exceptions;

public class FailedFileCloseException extends RuntimeException {
    public FailedFileCloseException() {
        super();
    }

    public FailedFileCloseException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Failed file closing";
    }
}
