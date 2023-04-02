package ru.nsu.bolotov.exceptions;

public class IncorrectScoreStringException extends RuntimeException {
    private final String scoreString;

    public IncorrectScoreStringException(String scoreString) {
        this.scoreString = scoreString;
    }

    @Override
    public String getMessage() {
        return "Incorrect score string: " + scoreString;
    }
}
