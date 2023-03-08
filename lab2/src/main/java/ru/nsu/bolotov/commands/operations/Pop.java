package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.ZeroArgs;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.EmptyStackException;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;

@CommandAnnotation
@ZeroArgs
public class Pop implements Command {
    private Double poppedValue;

    @Override
    public void execute(Object[] args, Context context) {
        checkArgs(args);
        try {
            poppedValue = context.popValueFromStack();
        } catch (RuntimeException exception) {
            throw new EmptyStackException();
        }
    }

    public Double getPoppedValue() {
        return poppedValue;
    }

    @Override
    public void checkArgs(Object[] args) {
        if (args.length != ZeroArgs.NUMBER_OF_ARGS) {
            throw new InvalidNumberOfArgsException(args.length, ZeroArgs.NUMBER_OF_ARGS);
        }
    }
}
