package ru.nsu.bolotov.exceptions;

public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Trying to get value from empty stack";
    }
}
