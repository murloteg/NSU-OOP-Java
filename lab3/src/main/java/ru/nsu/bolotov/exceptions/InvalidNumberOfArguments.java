package ru.nsu.bolotov.exceptions;

public class InvalidNumberOfArguments extends RuntimeException {
    private final int expectedNumber;
    private final int receivedNumber;

    public InvalidNumberOfArguments(int expectedNumber, int receivedNumber) {
        this.expectedNumber = expectedNumber;
        this.receivedNumber = receivedNumber;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid number of arguments: expected: %d, but received: %d", expectedNumber, receivedNumber);
    }
}
