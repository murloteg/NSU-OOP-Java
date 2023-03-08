package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.context.Context;

public interface Command {
    void execute(Object[] args, Context context);
    void checkArgs(Object[] args);
}
