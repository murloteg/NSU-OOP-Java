package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;

public class Print implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        System.out.println(context.peekInStack());
    }
}
