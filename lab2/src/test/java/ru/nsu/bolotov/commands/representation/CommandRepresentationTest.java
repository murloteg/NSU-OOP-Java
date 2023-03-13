package ru.nsu.bolotov.commands.representation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.commands.operations.Command;
import ru.nsu.bolotov.commands.operations.Push;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.factory.Factory;
import ru.nsu.bolotov.util.CommandUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommandRepresentationTest {
    private CommandRepresentation commandRepresentation;

    @Test
    void constructorTest() {
        assertDoesNotThrow(() -> {
            commandRepresentation = new CommandRepresentation(new Push(), new Object[] {});
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "POP",
            "DIVIDE",
            "MINUS",
            "MULTIPLY",
            "PLUS",
            "SQRT",
            "PRINT"
    }, delimiter = ':')
    void executeZeroArgTest(String command) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Context context = new Context();
        Push push = CommandUtils.createPushCommand();
        push.execute(new Number[] {2.0}, context);
        push.execute(new Number[] {1.0}, context);
        Command currentCommand = Factory.create(command);
        commandRepresentation = new CommandRepresentation(currentCommand, new Object[] {});
        assertDoesNotThrow(() -> commandRepresentation.execute(context));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "PUSH : 10",
            "COMMENT : some info"
    }, delimiter = ':')
    void executeSingleArgTest(String command, String singleArg) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Context context = new Context();
        Command currentCommand = Factory.create(command);
        commandRepresentation = new CommandRepresentation(currentCommand, new Object[] {singleArg});
        assertDoesNotThrow(() -> commandRepresentation.execute(context));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "DEFINE : value : 100"
    }, delimiter = ':')
    void executeTwoArgTest(String command, String firstArg, String secondArg) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Context context = new Context();
        Command currentCommand = Factory.create(command);
        commandRepresentation = new CommandRepresentation(currentCommand, new Object[] {firstArg, secondArg});
        assertDoesNotThrow(() -> commandRepresentation.execute(context));
    }
}
