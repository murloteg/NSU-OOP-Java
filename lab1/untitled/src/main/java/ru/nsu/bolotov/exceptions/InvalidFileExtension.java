package ru.nsu.bolotov.exceptions;

public class InvalidFileExtension extends Exception {
    public String getMessage() {
        return "Invalid file extension";
    }
}
