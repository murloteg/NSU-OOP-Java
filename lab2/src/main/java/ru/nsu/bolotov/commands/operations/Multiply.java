package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.ZeroArgs;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.util.CommandUtils;

@CommandAnnotation
@ZeroArgs
public class Multiply implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        checkArgs(args);
        Pop popCommand = CommandUtils.createPopCommand();
        popCommand.execute(args, context);
        double secondOperand = popCommand.getPoppedValue();
        popCommand.execute(args, context);
        double firstOperand = popCommand.getPoppedValue();

        Number[] elements = new Number[] {firstOperand * secondOperand};
        Push pushCommand = CommandUtils.createPushCommand();
        pushCommand.execute(elements, context);
    }

    @Override
    public void checkArgs(Object[] args) {
        if (args.length != ZeroArgs.NUMBER_OF_ARGS) {
            throw new InvalidNumberOfArgsException(args.length, ZeroArgs.NUMBER_OF_ARGS);
        }
    }
}
