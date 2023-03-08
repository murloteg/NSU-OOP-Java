package ru.nsu.bolotov.commands.operations;

import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.commands.annotations.ZeroArgs;
import ru.nsu.bolotov.context.Context;

@CommandAnnotation
@ZeroArgs
public class Comment implements Command {
    @Override
    public void execute(Object[] args, Context context) {
        /* just a comment. */
    }

    @Override
    public String toString() {
        return "COMMENT";
    }
}
