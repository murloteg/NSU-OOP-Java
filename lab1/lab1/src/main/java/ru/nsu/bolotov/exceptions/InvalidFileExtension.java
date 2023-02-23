package ru.nsu.bolotov.exceptions;

public class InvalidFileExtension extends Exception {
    @Override
    public String getMessage() {
        return "Invalid file extension";
    }
}
