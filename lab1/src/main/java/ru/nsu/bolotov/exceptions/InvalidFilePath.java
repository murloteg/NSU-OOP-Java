package ru.nsu.bolotov.exceptions;

public class InvalidFilePath extends RuntimeException {
    public InvalidFilePath() {
        super();
    }

    public InvalidFilePath(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Invalid file path";
    }
}
