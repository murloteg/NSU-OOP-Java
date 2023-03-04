package ru.nsu.bolotov.application;

import ru.nsu.bolotov.cmdlineparser.CommandLineParser;
import ru.nsu.bolotov.commands.Plus;
import ru.nsu.bolotov.commands.Pop;
import ru.nsu.bolotov.commands.Push;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.Factory;


import java.io.IOException;

import static ru.nsu.bolotov.util.UtilConsts.EMPTY;

public class Main {
    private static String path = EMPTY;
    public static void main(String[] args) { ///
        CommandLineParser.main(args);
        if (CommandLineParser.inputPath != null) {
            path = CommandLineParser.inputPath;
        }
        if (path.isEmpty()) {
            /* TODO: read from stdin */
        }
        else {
            /* TODO: read from file */
        }

        /* TODO: remove this block later */
        Context context = new Context();
        Push pushCommand;
        try {
            pushCommand = (Push) Factory.create("PUSH");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        assert pushCommand != null;
        pushCommand.execute(new Number[] {1.0}, context);
        pushCommand.execute(new Number[] {2.0}, context);

        Pop popCommand;
        try {
            popCommand = (Pop) Factory.create("POP");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }

        Plus plusCommand;
        try {
            plusCommand = (Plus) Factory.create("PLUS");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        assert plusCommand != null;
        plusCommand.execute(new Number[] {}, context);

        assert popCommand != null;
        popCommand.execute(new Number[] {}, context);
        double result = popCommand.getPoppedValue();
        System.out.println(result);
    }
}
