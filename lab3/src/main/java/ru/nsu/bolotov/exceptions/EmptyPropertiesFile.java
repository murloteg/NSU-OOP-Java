package ru.nsu.bolotov.exceptions;

public class EmptyPropertiesFile extends RuntimeException {
    @Override
    public String getMessage() {
        return "Empty properties file";
    }
}
