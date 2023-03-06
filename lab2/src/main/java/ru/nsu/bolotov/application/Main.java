package ru.nsu.bolotov.application;

import ru.nsu.bolotov.cmdlineparser.CommandLineParser;
import ru.nsu.bolotov.commands.*;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.Factory;
import ru.nsu.bolotov.util.CommandUtil;

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
        Command defineCommand;
        try {
            defineCommand = Factory.create("DEFINE");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        defineCommand.execute(new Object[] {'c', 4.0}, context);
        defineCommand.execute(new Object[] {'b', 2.0}, context);

        Command pushCommand = CommandUtil.createPushCommand();
        pushCommand.execute(new Object[] {'c'}, context);
        pushCommand.execute(new Object[] {'b'}, context);

        Command sqrt;
        try {
            sqrt = Factory.create("SQRT");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        sqrt.execute(new Object[] {}, context);
        Command print;
        try {
            print = Factory.create("PRINT");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        print.execute(new Object[]{}, context);
    }
}
