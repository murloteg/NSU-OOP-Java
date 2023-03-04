package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;

public interface Command {
    void execute(Number[] args, Context context);
}
