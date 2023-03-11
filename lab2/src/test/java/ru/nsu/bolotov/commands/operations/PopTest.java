package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.EmptyStackException;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;

import static org.junit.jupiter.api.Assertions.*;

class PopTest {
    private final Command pop = new Pop();

    @ParameterizedTest
    @CsvSource(value = {
            "10",
            "word",
            "..."
    })
    void incorrectPopExecuteTest(String args) {
        Context context = new Context();
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            pop.execute(args.split(" "), context);
        });
    }

    @Test
    void emptyStackCase() {
        Context context = new Context();
        assertThrows(EmptyStackException.class, () -> {
            pop.execute(new Object[] {}, context);
        });
    }

    @Test
    void toStringTest() {
        assertEquals("POP", pop.toString());
    }
}
