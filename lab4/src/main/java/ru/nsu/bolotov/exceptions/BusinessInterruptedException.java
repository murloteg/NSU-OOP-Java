package ru.nsu.bolotov.exceptions;

public class BusinessInterruptedException extends RuntimeException {
    @Override
    public String getMessage() {
        return String.format("Interrupted exception on thread %d", Thread.currentThread().getId());
    }
}
