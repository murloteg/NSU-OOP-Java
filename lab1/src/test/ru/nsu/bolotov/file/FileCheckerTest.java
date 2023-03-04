package ru.nsu.bolotov.file;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.bolotov.exceptions.InvalidFileExtension;

import static org.junit.jupiter.api.Assertions.*;

class FileCheckerTest {
    @ParameterizedTest
    @ValueSource(strings = {"bad.123.txt", "oops+oops", "input.pdf", "wro.ng", "."})
    void checkBadExtensionsTest(String path) {
        checker = new FileChecker(path);
        assertThrows(InvalidFileExtension.class, () -> {
           checker.checkExtension();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"good.txt", "another", "doc.doc", "123"})
    void checkAvailableExtensionsTest(String path) {
        checker = new FileChecker(path);
        assertDoesNotThrow(() -> {
            checker.checkExtension();
        });
    }
    private FileChecker checker;
}
