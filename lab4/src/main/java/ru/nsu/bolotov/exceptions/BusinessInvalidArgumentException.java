package ru.nsu.bolotov.exceptions;

public class BusinessInvalidArgumentException extends RuntimeException {
    private final Number leftBoundary;
    private final Number rightBoundary;
    private final Number received;
    
    public BusinessInvalidArgumentException(Number leftBoundary, Number rightBoundary, Number received) {
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.received = received;
    }
    
    @Override
    public String getMessage() {
        return String.format("Invalid argument: %s! It's doesn't contained in [%s, %s]", received, leftBoundary, rightBoundary);
    }
}
