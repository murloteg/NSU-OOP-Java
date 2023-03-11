package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.UndefinedAliasInDefinitionMap;

import static org.junit.jupiter.api.Assertions.*;

class PushTest {
    private final Command push = new Push();

    @ParameterizedTest
    @CsvSource(value = {
            "10 abc",
            "word word word",
    })
    void incorrectPushExecuteTest(String args) {
        Context context = new Context();
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            push.execute(args.split(" "), context);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "val",
            "another",
            "null",
    })
    void undefinedAliasInDefinitionMapCase(String args) {
        Context context = new Context();
        assertThrows(UndefinedAliasInDefinitionMap.class, () -> {
            push.execute(new Object[] {args}, context);
        });
    }

    @Test
    void toStringTest() {
        assertEquals("PUSH", push.toString());
    }
}
