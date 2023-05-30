package ru.nsu.bolotov.exceptions;

public class IOBusinessException extends RuntimeException {
    private final String message;

    public IOBusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
