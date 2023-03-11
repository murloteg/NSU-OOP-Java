package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.util.CommandUtils;

import static org.junit.jupiter.api.Assertions.*;

class SqrtTest {
    private final Command sqrt = new Sqrt();

    @ParameterizedTest
    @CsvSource(value = {
            "10",
            "value",
            "null null"
    })
    void incorrectSqrtExecuteTest(String args) {
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            Context context = new Context();
            sqrt.execute(args.split(" "), context);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0",
            "4",
            "256",
            "2",
            "0.5",
            "1000000"
    })
    void operationTest(String firstVal) {
        Context context = new Context();
        Push push = CommandUtils.createPushCommand();
        push.execute(new Object[] {firstVal}, context);

        sqrt.execute(new Object[] {}, context);
        Pop pop = CommandUtils.createPopCommand();
        pop.execute(new Object[] {}, context);
        assertEquals(Math.sqrt(Double.parseDouble(firstVal)), pop.getPoppedValue());
    }

    @Test
    void toStringTest() {
        assertEquals("SQRT", sqrt.toString());
    }
}
