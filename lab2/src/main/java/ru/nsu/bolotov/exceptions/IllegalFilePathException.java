package ru.nsu.bolotov.exceptions;

public class IllegalFilePathException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Illegal file path";
    }
}
