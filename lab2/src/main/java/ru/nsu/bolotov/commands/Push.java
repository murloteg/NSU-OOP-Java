package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.UndefinedAliasInDefinitionMap;

public class Push implements Command {
    @Override
    public void execute(Object[] args, Context context) throws UndefinedAliasInDefinitionMap {
        if (args.length == 0) {
            throw new InvalidNumberOfArgsException();
        }
        Double value;
        if (args[0] instanceof Character) {
            value = context.getDefinitionFromMap((Character) args[0]);
        }
        else {
            value = (Double) args[0];
        }
        if (value == null) {
            throw new UndefinedAliasInDefinitionMap();
        }
        context.addValueToStack(value);
    }
}
