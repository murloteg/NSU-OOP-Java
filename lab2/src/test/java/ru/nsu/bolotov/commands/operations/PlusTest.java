package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.util.CommandUtils;

import static org.junit.jupiter.api.Assertions.*;

class PlusTest {
    private final Command plus = new Plus();

    @ParameterizedTest
    @CsvSource(value = {
            "10",
            "value",
            "null null"
    })
    void incorrectPlusExecuteTest(String args) {
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            Context context = new Context();
           plus.execute(args.split(" "), context);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10 ? +10",
            "100 ? 100",
            "-100 ? 100",
            "+0 ? -0",
            "-0 ? -0",
            "+0.5 ? +0.5"
    }, delimiter = '?')
    void operationTest(String firstVal, String secondVal) {
        Context context = new Context();
        Push push = CommandUtils.createPushCommand();
        push.execute(new Object[] {firstVal}, context);
        push.execute(new Object[] {secondVal}, context);

        plus.execute(new Object[] {}, context);
        Pop pop = CommandUtils.createPopCommand();
        pop.execute(new Object[] {}, context);
        assertEquals(Double.parseDouble(firstVal) + Double.parseDouble(secondVal), pop.getPoppedValue());
    }

    @Test
    void toStringTest() {
        assertEquals("PLUS", plus.toString());
    }
}
