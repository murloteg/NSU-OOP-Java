package ru.nsu.bolotov.exceptions;

public class InvalidInstanceOfException extends RuntimeException {
    public String getMessage() {
        return "Invalid instanceof";
    }
}
