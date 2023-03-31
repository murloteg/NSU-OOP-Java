package ru.nsu.bolotov.exceptions.gui;

public class InvalidImagePathException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid image path";
    }
}
