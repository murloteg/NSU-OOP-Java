package ru.nsu.bolotov.exceptions;

public class UnexpectedArgumentType extends IllegalArgumentException {
    private final String expectedType;
    private final String receivedType;

    public UnexpectedArgumentType(String expectedType, String receivedType) {
        this.expectedType = expectedType;
        this.receivedType = receivedType;
    }

    @Override
    public String getMessage() {
        return String.format("Expected type: %s, but received type: %s", expectedType, receivedType);
    }
}
