package ru.nsu.bolotov.util;

public abstract class LoggerUtilStringConsts {
    public static final String COMMAND_WAS_PARSED = "Command line \"{}\" was parsed from input stream.";

    private LoggerUtilStringConsts() {
        throw new IllegalStateException("Instantiation of abstract class");
    }
}
