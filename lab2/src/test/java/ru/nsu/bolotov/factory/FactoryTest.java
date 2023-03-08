package ru.nsu.bolotov.factory;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.commands.operations.Command;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class FactoryTest {
    @ParameterizedTest
    @CsvSource(value = {
            "POP",
            "DIVIDE",
            "PRINT",
            "PUSH",
            "MULTIPLY"
    })
    void commonCreationInstanceOfCommand(String commandName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        assertInstanceOf(Command.class, Factory.create(commandName));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null",
            "undefined",
            "00000",
            "pop"
    })
    void invalidCreationGeneratesNull(String commandName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        assertNull(Factory.create(commandName));
    }
}
