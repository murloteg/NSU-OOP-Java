package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.factory.Factory;
import ru.nsu.bolotov.exceptions.FailedCreationException;

import java.io.IOException;

public class Plus implements Command {
    @Override
    public void execute(Number[] args, Context context) {
        Pop popCommand;
        try {
            popCommand = (Pop) Factory.create("POP");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        assert popCommand != null;
        popCommand.execute(args, context);
        double firstOperand = popCommand.getPoppedValue();
        popCommand.execute(args, context);
        double secondOperand = popCommand.getPoppedValue();

        Push pushCommand;
        try {
            pushCommand = (Push) Factory.create("PUSH");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        Number[] elements = new Number[] {firstOperand + secondOperand};
        assert pushCommand != null;
        pushCommand.execute(elements, context);
    }
}
