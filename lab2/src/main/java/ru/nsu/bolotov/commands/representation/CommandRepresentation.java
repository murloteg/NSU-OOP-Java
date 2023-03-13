package ru.nsu.bolotov.commands.representation;

import ru.nsu.bolotov.commands.operations.Command;
import ru.nsu.bolotov.context.Context;
import javax.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CommandRepresentation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRepresentation.class);
    private final Map<Command, Object[]> representation = new HashMap<>();

    public CommandRepresentation(@Nonnull Command command, @Nonnull Object[] args) {
        representation.put(command, args);
    }

    public void execute(Context context) {
        for (Map.Entry<Command, Object[]> entry : representation.entrySet()) {
            LOGGER.info("Execution \"{}\" with args: {}", entry.getKey(), entry.getValue());
            try {
                entry.getKey().execute(entry.getValue(), context);
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage());
            }
        }
    }
}
