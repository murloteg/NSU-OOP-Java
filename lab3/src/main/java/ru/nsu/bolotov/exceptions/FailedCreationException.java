package ru.nsu.bolotov.exceptions;

public class FailedCreationException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Failed creation";
    }
}
