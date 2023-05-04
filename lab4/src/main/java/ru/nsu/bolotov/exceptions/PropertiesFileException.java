package ru.nsu.bolotov.exceptions;

public class PropertiesFileException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Exception during work with properties file";
    }
}
