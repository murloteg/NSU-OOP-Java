package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.ZeroArgs;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;

@CommandAnnotation
@ZeroArgs
public class Print implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        checkArgs(args);
        System.out.println(context.peekInStack());
    }

    @Override
    public void checkArgs(Object[] args) {
        if (args.length != ZeroArgs.NUMBER_OF_ARGS) {
            throw new InvalidNumberOfArgsException(args.length, ZeroArgs.NUMBER_OF_ARGS);
        }
    }
}
