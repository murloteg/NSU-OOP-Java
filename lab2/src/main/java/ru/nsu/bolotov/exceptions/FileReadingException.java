package ru.nsu.bolotov.exceptions;

public class FileReadingException extends RuntimeException {
    @Override
    public String getMessage() {
        return "File reading exception";
    }
}
