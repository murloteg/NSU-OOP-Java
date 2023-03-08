package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.ZeroArgs;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;

@CommandAnnotation
@ZeroArgs
public class Comment implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        /* just a comment. */
    }

    @Override
    public void checkArgs(Object[] args) {
        if (args.length != ZeroArgs.NUMBER_OF_ARGS) {
            throw new InvalidNumberOfArgsException();
        }
    }
}
