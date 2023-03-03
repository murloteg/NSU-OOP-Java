package ru.nsu.bolotov.exceptions;

public class IncorrectPropertyFile extends RuntimeException {
    public String getMessage() {
        return "Incorrect property file";
    }
}
