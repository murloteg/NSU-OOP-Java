package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {
    private final Command exit = new Exit();

    @ParameterizedTest
    @CsvSource(value = {
            "10",
            "word",
            "..."
    })
    void incorrectExitExecuteTest(String args) {
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            Context context = new Context();
            exit.execute(args.split(" "), context);
        });
    }

    @Test
    void toStringTest() {
        assertEquals("EXIT", exit.toString());
    }
}
