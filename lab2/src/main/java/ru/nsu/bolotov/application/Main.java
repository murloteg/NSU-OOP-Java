package ru.nsu.bolotov.application;

import ru.nsu.bolotov.cmdlineparser.CommandLineParser;

import static ru.nsu.bolotov.util.UtilConsts.EMPTY;

public class Main {
    private static String path = EMPTY;
    public static void main(String[] args) {
        CommandLineParser.main(args);
        if (CommandLineParser.inputPath != null) {
            path = CommandLineParser.inputPath;
        }
        if (path.isEmpty()) {
            /* TODO: read from stdin */
        }
        else {
            /* TODO: read from file */
        }
    }
}
