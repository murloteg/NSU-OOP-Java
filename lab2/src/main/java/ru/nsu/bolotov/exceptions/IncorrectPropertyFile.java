package ru.nsu.bolotov.exceptions;

public class IncorrectPropertyFile extends RuntimeException {
    @Override
    public String getMessage() {
        return "Incorrect property file";
    }
}
