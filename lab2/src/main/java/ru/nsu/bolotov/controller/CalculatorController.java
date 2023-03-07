package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.parserofcommands.ParserOfCommands;
import ru.nsu.bolotov.commands.CommandRepresentation;
import ru.nsu.bolotov.context.Context;

public class CalculatorController {
    private final ParserOfCommands parser;
    private final Context context = new Context();

    public CalculatorController() {
        parser = new ParserOfCommands();
    }

    public CalculatorController(String path) {
        parser = new ParserOfCommands(path);
    }

    public void calculation() {
        String nextCommandLine = parser.getNextString();
        while (nextCommandLine != null) {
            CommandRepresentation commandRepresentation = parser.getNextCommand(nextCommandLine);
            commandRepresentation.execute(context);
            nextCommandLine = parser.getNextString();
        }
    }
}
