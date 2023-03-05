package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidArgsException;

public class Define implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        if (args.length != 2) {
            throw new InvalidArgsException();
        }
        if (!(args[0] instanceof Character) || !(args[1] instanceof Number)) {
            throw new InvalidArgsException();
        }
        context.addDefinitionToMap((Character) args[0], (Double) args[1]);
    }
}
