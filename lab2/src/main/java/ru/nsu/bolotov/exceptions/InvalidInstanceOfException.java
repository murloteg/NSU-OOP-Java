package ru.nsu.bolotov.exceptions;

public class InvalidInstanceOfException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid instanceof";
    }
}
