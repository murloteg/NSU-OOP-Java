package ru.nsu.bolotov.exceptions;

public class FailedUserInterfaceInitializationException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Something went wrong while initializing the user interface";
    }
}
