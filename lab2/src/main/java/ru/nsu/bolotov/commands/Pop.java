package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.EmptyStackException;

public class Pop implements Command {
    private Double poppedValue;

    @Override
    public void execute(Object[] args, Context context) {
        try {
            poppedValue = context.popValueFromStack();
        } catch (RuntimeException exception) {
            throw new EmptyStackException();
        }
    }

    public Double getPoppedValue() {
        return poppedValue;
    }
}
