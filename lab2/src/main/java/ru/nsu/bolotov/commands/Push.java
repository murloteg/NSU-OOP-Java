package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidArgsException;

public class Push implements Command {
    @Override
    public void execute(Number[] args, Context context) {
        if (args.length == 0) {
            throw new InvalidArgsException();
        }

        Double value = (Double) args[0];
        context.addValueToStack(value);
    }
}
