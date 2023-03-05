package ru.nsu.bolotov.application;

import ru.nsu.bolotov.cmdlineparser.CommandLineParser;
import ru.nsu.bolotov.commands.*;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.Factory;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        Command defineCommand;
        try {
            defineCommand = Factory.create("DEFINE");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        assert defineCommand != null;
        defineCommand.execute(new Object[] {'c', 4.0}, context);
        defineCommand.execute(new Object[] {'b', 10.0}, context);

        Command pushCommand;
        try {
            pushCommand = (Push) Factory.create("PUSH");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        assert pushCommand != null;
        pushCommand.execute(new Object[] {'c'}, context);
        pushCommand.execute(new Object[] {'b'}, context);

        Command popCommand;
        try {
            popCommand = (Pop) Factory.create("POP");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }

        Command plusCommand;
        try {
            plusCommand = (Plus) Factory.create("PLUS");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        assert plusCommand != null;
        plusCommand.execute(new Number[] {}, context);

        assert popCommand != null;
        popCommand.execute(new Number[] {}, context);

        Method method;
        try {
            method = popCommand.getClass().getMethod("getPoppedValue");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Object value;
        try {
            value = method.invoke(popCommand);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        System.out.println(value);
    }
}
