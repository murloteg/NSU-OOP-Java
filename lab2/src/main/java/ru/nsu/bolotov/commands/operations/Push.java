package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.SingleArg;
import ru.nsu.bolotov.commands.annotations.ZeroArgs;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.UndefinedAliasInDefinitionMap;

@CommandAnnotation
@SingleArg
public class Push implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        checkArgs(args);
        Double value = null;
        if (args[0] instanceof String) {
            try {
                value = Double.parseDouble((String) args[0]);
            } catch (NumberFormatException exception) {
                value = context.getDefinitionFromMap((String) args[0]);
            }
        } else if (args[0] instanceof Double) {
            value = (Double) args[0];
        }

        if (value == null) {
            throw new UndefinedAliasInDefinitionMap();
        }
        context.addValueToStack(value);
    }

    @Override
    public void checkArgs(Object[] args) {
        if (args.length != SingleArg.NUMBER_OF_ARGS) {
            throw new InvalidNumberOfArgsException(args.length, SingleArg.NUMBER_OF_ARGS);
        }
    }

    @Override
    public String toString() {
        return "PUSH";
    }
}
