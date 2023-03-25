package ru.nsu.bolotov.cmdlineparser;

import org.apache.commons.cli.*;
import ru.nsu.bolotov.exceptions.ParsingException;
import ru.nsu.bolotov.exceptions.UnexpectedArgumentType;
import ru.nsu.bolotov.util.UtilConsts;

import java.io.PrintWriter;

public class CommandLineParser {
    private static final Option VIEW_TYPE_ARG = new Option("v", "view", true, "Type of game-view");
    private static final Option FIELD_SIZE_ARG = new Option("s", "size", true, "Field size");
    private static final Option NUMBER_OF_BOMBS = new Option("n", "number", true, "Number of bombs");
    private String typeOfView;
    private int fieldSize = UtilConsts.FieldConsts.DEFAULT_FIELD_SIZE;
    private int numberOfBombs = UtilConsts.FieldConsts.DEFAULT_NUMBER_OF_MINES;

    public CommandLineParser() {
        VIEW_TYPE_ARG.setArgs(1);
        VIEW_TYPE_ARG.setRequired(true);
        FIELD_SIZE_ARG.setArgs(1);
        NUMBER_OF_BOMBS.setArgs(1);
    }

    public void parseCommandLine(String[] args) {
        Options options = new Options();
        options.addOption(VIEW_TYPE_ARG);
        options.addOption(FIELD_SIZE_ARG);
        options.addOption(NUMBER_OF_BOMBS);

        DefaultParser cmdLineParser = new DefaultParser();
        try {
            CommandLine commandLine = cmdLineParser.parse(options, args);
            if (!commandLine.hasOption(VIEW_TYPE_ARG.getLongOpt())) {
                printHelp(options);
                throw new ParsingException();
            } else {
                typeOfView = commandLine.getOptionValue(VIEW_TYPE_ARG.getLongOpt());
            }

            if (commandLine.hasOption(FIELD_SIZE_ARG.getLongOpt())) {
                fieldSize = Integer.parseInt(commandLine.getOptionValue(FIELD_SIZE_ARG.getLongOpt()));
            }

            if (commandLine.hasOption(NUMBER_OF_BOMBS.getLongOpt())) {
                numberOfBombs = Integer.parseInt(commandLine.getOptionValue(NUMBER_OF_BOMBS.getLongOpt()));
            }
        } catch (ParseException exception) {
            printHelp(options);
            throw new ParsingException();
        }
    }

    public int getTypeOfView() {
        if (typeOfView.toUpperCase().contains("TEXT")) {
            return UtilConsts.ViewTypes.TEXT;
        } else if (typeOfView.toUpperCase().contains("GRAPHIC") || typeOfView.toUpperCase().contains("GUI")) {
            return UtilConsts.ViewTypes.GRAPHIC_INTERFACE;
        } else {
            throw new UnexpectedArgumentType("Some kind of view", typeOfView);
        }
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        formatter.printUsage(printWriter, 100, "[REQ_OPTION] view [NON_REQ_OPTION] field_size [NON_REQ_OPTION] number_of_bombs");
        formatter.printOptions(printWriter, 100, options, 2, 20);
        printWriter.close();
    }
}
