package ru.nsu.bolotov.exceptions;

public class ParsingException extends RuntimeException {
    public ParsingException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Failed parsing from command line";
    }
}
