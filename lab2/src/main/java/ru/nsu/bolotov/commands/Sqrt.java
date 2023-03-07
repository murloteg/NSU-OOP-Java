package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.util.CommandUtils;

public class Sqrt implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        Pop popCommand = CommandUtils.createPopCommand();
        popCommand.execute(args, context);
        double currentOperand = popCommand.getPoppedValue();

        Number[] elements = new Number[] {Math.sqrt(currentOperand)};
        Push pushCommand = CommandUtils.createPushCommand();
        pushCommand.execute(elements, context);
    }
}
