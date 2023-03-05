package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;

public interface Command {
    void execute(Object[] args, Context context);
}
