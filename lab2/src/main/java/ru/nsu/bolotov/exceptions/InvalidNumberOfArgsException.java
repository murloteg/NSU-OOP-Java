package ru.nsu.bolotov.exceptions;

public class InvalidNumberOfArgsException extends RuntimeException {
    private int receivedNumber;
    private int expectedNumber;

    public InvalidNumberOfArgsException(int receivedNumber, int expectedNumber) {
        this.receivedNumber = receivedNumber;
        this.expectedNumber = expectedNumber;
    }

    @Override
    public String getMessage() {
        return "Invalid number of args: expected " + expectedNumber + ", but received " + receivedNumber;
    }
}
