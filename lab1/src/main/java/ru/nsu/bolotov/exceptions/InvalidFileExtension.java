package ru.nsu.bolotov.exceptions;

public class InvalidFileExtension extends RuntimeException {
    public InvalidFileExtension() {
        super();
    }

    public InvalidFileExtension(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Invalid file extension";
    }
}
