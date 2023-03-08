package ru.nsu.bolotov.util;

import ru.nsu.bolotov.commands.operations.Pop;
import ru.nsu.bolotov.commands.operations.Push;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.Factory;

import java.io.IOException;

public abstract class CommandUtils {
    public static Pop createPopCommand() {
        Pop popCommand;
        try {
            popCommand = (Pop) Factory.create("POP");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        return popCommand;
    }

    public static Push createPushCommand() {
        Push pushCommand;
        try {
            pushCommand = (Push) Factory.create("PUSH");
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new FailedCreationException(exception.getMessage());
        }
        return pushCommand;
    }

    private CommandUtils() {
        throw new IllegalStateException("Util class");
    }
}
