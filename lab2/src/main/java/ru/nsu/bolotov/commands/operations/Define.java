package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.TwoArgs;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;

@CommandAnnotation
@TwoArgs
public class Define implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        checkArgs(args);
        if (args[0] instanceof String && args[1] instanceof String) {
            context.addDefinitionToMap((String) args[0], Double.valueOf((String) args[1]));
        } else {
            throw new InvalidTypeOfArgumentException((String[]) args);
        }
    }

    @Override
    public void checkArgs(Object[] args) {
        if (args.length != TwoArgs.NUMBER_OF_ARGS) {
            throw new InvalidNumberOfArgsException();
        }
    }
}
