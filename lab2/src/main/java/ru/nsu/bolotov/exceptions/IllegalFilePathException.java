package ru.nsu.bolotov.exceptions;

public class IllegalFilePathException extends RuntimeException {
    public IllegalFilePathException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Illegal file path";
    }
}
