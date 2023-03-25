package ru.nsu.bolotov.exceptions;

public class IOBusinessException extends RuntimeException{
    public IOBusinessException(Throwable exception) {
        super(exception);
    }

    @Override
    public String getMessage() {
        return "Failed IO operation";
    }
}
