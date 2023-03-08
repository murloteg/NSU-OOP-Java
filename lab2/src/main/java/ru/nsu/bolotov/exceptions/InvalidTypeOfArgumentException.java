package ru.nsu.bolotov.exceptions;

public class InvalidTypeOfArgumentException extends RuntimeException {
    private final String[] separatedCommand;

    public InvalidTypeOfArgumentException(String[] separatedCommand) {
        this.separatedCommand = separatedCommand;
    }

    @Override
    public String getMessage() {
        return "Invalid type of argument: \"" + separatedCommand[0] + "\"";
    }
}
