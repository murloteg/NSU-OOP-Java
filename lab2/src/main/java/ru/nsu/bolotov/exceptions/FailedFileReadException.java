package ru.nsu.bolotov.exceptions;

public class FailedFileReadException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Failed reading from file";
    }
}
