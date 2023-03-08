package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArgsException;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;

import static org.junit.jupiter.api.Assertions.*;

// TODO: add mockito-context.
class DefineTest {
    private Command define = new Define();

    @ParameterizedTest
    @CsvSource(value = {
            "alias text",
            "long 100L",
            "single 0+1.0"
    })
    void defineIncorrectExecuteTestWithStringArgs(String args) {
        assertThrows(InvalidTypeOfArgumentException.class, () -> {
           define.execute(args.split(" "), null);
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "100 200",
            "0 0",
            "-1000 900"
    })
    void defineIncorrectExecuteTestWithNonStringArgs(String args) {
        assertThrows(InvalidTypeOfArgumentException.class, () -> {
            String[] separatedLine = args.split(" ");
            Number[] array = new Number[] {Integer.parseInt(separatedLine[0]), Integer.parseInt(separatedLine[1])};
            define.execute(array, null);
        });
    }

    @Test
    void toStringTest() {
        assertEquals("DEFINE", define.toString());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "value 4",
            "another 10",
            "x -100",
            "zero -0"
    })
    void checkCorrectArgsTest(String args) {
        assertDoesNotThrow(() -> {
           define.checkArgs(args.split(" "));
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "value 4 5",
            "another 10 -4 -0",
            "x"
    })
    void checkIncorrectArgsTest(String args) {
        assertThrows(InvalidNumberOfArgsException.class, () -> {
            define.checkArgs(args.split(" "));
        });
    }
}
