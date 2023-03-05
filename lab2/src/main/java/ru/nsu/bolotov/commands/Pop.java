package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;

public class Pop implements Command {
    private Double poppedValue;

    @Override
    public void execute(Object[] args, Context context) {
        poppedValue = context.popValueFromStack();
    }

    public Double getPoppedValue() {
        return poppedValue;
    }
}
