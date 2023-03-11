package ru.nsu.bolotov.exceptions;

public class IncorrectPropertyFile extends RuntimeException {
    public IncorrectPropertyFile() {
        super();
    }

    @Override
    public String getMessage() {
        return "Incorrect property file";
    }
}
