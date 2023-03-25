package ru.nsu.bolotov.application;

import ru.nsu.bolotov.cmdlineparser.CommandLineParser;

public class ApplicationStarter {
    private final String[] args;
    private int typeOfView;
    private int fieldSize;
    private int numberOfBombs;

    public ApplicationStarter(String[] args) {
        this.args = args;
    }

    public void prepareArgs() {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.parseCommandLine(args);
        typeOfView = commandLineParser.getTypeOfView();
        fieldSize = commandLineParser.getFieldSize();
        numberOfBombs = commandLineParser.getNumberOfBombs();
    }

    public int getTypeOfView() {
        return typeOfView;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }
}
