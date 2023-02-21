package ru.nsu.bolotov.file;

import org.junit.jupiter.api.Test;
import ru.nsu.bolotov.exceptions.InvalidFileExtension;

import static org.junit.jupiter.api.Assertions.*;
public class FileCheckerTest {
    @Test
    void checkBadExtensionsTest() throws InvalidFileExtension {
        String[] fileNames = new String[] {"sampl.e.txt", "in.docx", "a.pdf", "qwerty+2"};
        for (String fileName : fileNames) {
            checker = new FileChecker(fileName);
            Exception exception = assertThrows(InvalidFileExtension.class, () -> checker.checkExtension()); // TODO!
        }
    }
    @Test
    void checkAvailableExtensionsTest() {

    }
    private FileChecker checker;
}
