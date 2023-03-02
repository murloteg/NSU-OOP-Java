package ru.nsu.bolotov.exceptions;

public class InvalidArgsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid args";
    }
}
