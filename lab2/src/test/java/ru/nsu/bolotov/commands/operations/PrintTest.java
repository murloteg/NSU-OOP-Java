package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;

import static org.junit.jupiter.api.Assertions.*;

class PrintTest {
    private final Command print = new Print();

    @ParameterizedTest
    @CsvSource(value = {
            "10",
            "value",
            "null null"
    })
    void incorrectPrintExecuteTest(String args) {
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            Context context = new Context();
            print.execute(args.split(" "), context);
        });
    }

    @Test
    void toStringTest() {
        assertEquals("PRINT", print.toString());
    }
}
