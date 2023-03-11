package ru.nsu.bolotov.exceptions;

public class FailedCreationException extends RuntimeException {
    public FailedCreationException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Failed creation in factory";
    }
}
