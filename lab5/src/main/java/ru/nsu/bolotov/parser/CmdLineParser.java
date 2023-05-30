package ru.nsu.bolotov.parser;

import org.apache.commons.cli.*;
import ru.nsu.bolotov.exceptions.IOBusinessException;

public class CmdLineParser {
    private static final Option TYPE = new Option("t", "type", true, "Type of host: client or server");
    private String parsedType;

    public CmdLineParser() {
        TYPE.setArgs(1);
        TYPE.setRequired(true);
    }

    public void parseCmdLine(String[] args) {
        DefaultParser cmdLineParser = new DefaultParser();
        Options options = new Options();
        options.addOption(TYPE);

        CommandLine commandLine;
        try {
            commandLine = cmdLineParser.parse(options, args);
            if (!commandLine.hasOption(TYPE.getLongOpt())) {
                throw new ParseException("Incorrect parameters in command line");
            }
        } catch (ParseException exception) {
            throw new IOBusinessException(exception.getMessage());
        }

        if (commandLine.hasOption(TYPE.getLongOpt())) {
            parsedType = commandLine.getOptionValue(TYPE.getOpt());
        }
    }

    public String getType() {
        return parsedType;
    }
}
