package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.util.CommandUtils;

public class Plus implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        Pop popCommand = CommandUtils.createPopCommand();
        popCommand.execute(args, context);
        double secondOperand = popCommand.getPoppedValue();
        popCommand.execute(args, context);
        double firstOperand = popCommand.getPoppedValue();

        Number[] elements = new Number[] {firstOperand + secondOperand};
        Push pushCommand = CommandUtils.createPushCommand();
        pushCommand.execute(elements, context);
    }
}
