package ru.nsu.bolotov.exceptions;

public class EmptyStackException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Trying to get value from empty stack";
    }
}
