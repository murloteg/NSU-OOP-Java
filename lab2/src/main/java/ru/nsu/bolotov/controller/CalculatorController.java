package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.parserofcommands.ParserOfCommands;
import ru.nsu.bolotov.commands.representation.CommandRepresentation;
import ru.nsu.bolotov.context.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.nsu.bolotov.util.LoggerUtilStringConsts.COMMAND_WAS_PARSED;

public class CalculatorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);
    private final ParserOfCommands parser;
    private final Context context = new Context();

    public CalculatorController() {
        LOGGER.trace("Constructor of CalculateController without args was invoked.");
        parser = new ParserOfCommands();
        LOGGER.trace("Constructor of CalculateController without args finished job.");
    }

    public CalculatorController(String path) {
        LOGGER.trace("Constructor of CalculateController with [String] arg={} was invoked.", path);
        parser = new ParserOfCommands(path);
        LOGGER.trace("Constructor of CalculateController with [String] arg={} finished job.", path);
    }

    public void calculation() {
        String nextCommandLine = parser.getNextString();
        LOGGER.info(COMMAND_WAS_PARSED, nextCommandLine);
        while (nextCommandLine != null) {
            CommandRepresentation commandRepresentation;
            try {
                commandRepresentation = parser.getNextCommand(nextCommandLine);
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage());
                nextCommandLine = parser.getNextString();
                LOGGER.info(COMMAND_WAS_PARSED, nextCommandLine);
                continue;
            }
            commandRepresentation.execute(context);
            nextCommandLine = parser.getNextString();
            LOGGER.info(COMMAND_WAS_PARSED, nextCommandLine);
        }
    }
}
