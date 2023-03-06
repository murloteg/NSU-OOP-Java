package ru.nsu.bolotov.exceptions;

public class InvalidNumberOfArgsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid number of args";
    }
}
