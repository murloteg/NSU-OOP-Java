package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.UndefinedAliasInDefinitionMap;

public class Push implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        if (args.length == 0) {
            throw new InvalidNumberOfArgsException();
        }
        Double value = null;
        if (args[0] instanceof String) {
            try {
                value = Double.parseDouble((String) args[0]);
            } catch (NumberFormatException exception) {
                value = context.getDefinitionFromMap(((String) args[0]).charAt(0));
            }
        }
        else if (args[0] instanceof Double) {
            value = (Double) args[0];
        }

        if (value == null) {
            throw new UndefinedAliasInDefinitionMap();
        }
        context.addValueToStack(value);
    }
}
