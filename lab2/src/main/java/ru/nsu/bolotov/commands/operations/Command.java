package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.context.Context;

public interface Command {
    void execute(Object[] args, Context context);
    default void checkArgs(Object[] args) {

    }
}
