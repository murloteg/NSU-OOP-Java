package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;

public class Define implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        if (args.length != 2) {
            throw new InvalidNumberOfArgsException();
        }
        if (args[0] instanceof String && args[1] instanceof String) {
            context.addDefinitionToMap(((String) args[0]).charAt(0), Double.valueOf((String) args[1]));
        } else {
            throw new InvalidTypeOfArgumentException();
        }
    }
}
