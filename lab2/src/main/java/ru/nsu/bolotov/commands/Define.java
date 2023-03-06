package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;

public class Define implements Command {
    @Override
    public void execute(Object[] args, Context context) throws InvalidNumberOfArgsException, InvalidTypeOfArgumentException {
        if (args.length != 2) {
            throw new InvalidNumberOfArgsException();
        }
        if (!(args[0] instanceof Character) || !(args[1] instanceof Number)) {
            throw new InvalidTypeOfArgumentException();
        }
        context.addDefinitionToMap((Character) args[0], (Double) args[1]);
    }
}
