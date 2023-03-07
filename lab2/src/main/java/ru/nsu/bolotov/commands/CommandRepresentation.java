package ru.nsu.bolotov.commands;

import ru.nsu.bolotov.context.Context;

import java.util.HashMap;
import java.util.Map;

public class CommandRepresentation {
    private final Map<Command, Object[]> representation = new HashMap<>();

    public CommandRepresentation(Command command, Object[] args) {
        representation.put(command, args);
    }

    public void execute(Context context) {
        for (Map.Entry<Command, Object[]> entry : representation.entrySet()) {
            entry.getKey().execute(entry.getValue(), context);
        }
    }
}
