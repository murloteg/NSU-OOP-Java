package ru.nsu.bolotov.parserofcommands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.commands.representation.CommandRepresentation;
import ru.nsu.bolotov.exceptions.IllegalFilePathException;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ParserOfCommandsTest {
    private ParserOfCommands parser;

    @Test
    void constructorWithoutArgsTest() {
        assertDoesNotThrow(() -> new ParserOfCommands());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "test-input-first.txt",
            "test-input-second.txt"
    })
    void constructorWithPathTest(String path) {
        assertDoesNotThrow(() -> new ParserOfCommands(path));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "123.txt",
            "null",
            ".txt"
    })
    void constructorWithIllegalPathTest(String illegalPath) {
        assertThrows(IllegalFilePathException.class, () -> {
            new ParserOfCommands(illegalPath);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "test-input-first.txt : PRINT",
            "test-input-second.txt : DEFINE value 9"
    }, delimiter = ':')
    void getNextStringTest(String path, String expected) {
        parser = new ParserOfCommands(path);
        assertEquals(expected, parser.getNextString());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "PUSH 100",
            "DEFINE value 9",
            "POP",
            "COMMENT",
            "DIVIDE",
            "MINUS",
            "MULTIPLY",
            "PLUS",
            "SQRT",
            "PRINT"
    })
    void correctGetNextCommandTest(String commandLine) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        parser = new ParserOfCommands();
        assertInstanceOf(CommandRepresentation.class, parser.getNextCommand(commandLine));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "success",
            "null",
            "NULL",
            "...",
            "12345"
    })
    void incorrectGetNextCommandTest(String commandLine) {
        parser = new ParserOfCommands();
        assertThrows(InvalidTypeOfArgumentException.class, () -> {
            parser.getNextCommand(commandLine);
        });
    }
}
