package ru.nsu.bolotov.file;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.bolotov.utility.UtilityStringConsts.EMPTY;

class MyFileReaderTest {
    @ParameterizedTest
    @ValueSource(strings = {"illegal.txt", "unavailable.doc", "a.out"})
    void constructorThrowsExceptionTest(String path) {
        assertThrows(FileNotFoundException.class, () -> {
            new MyFileReader(path);
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reader-test-source.csv", numLinesToSkip = 1, delimiter = ':')
    void getNextWordTest(String input, String expected) throws IOException {
        fileReader = new MyFileReader(input);
        ArrayList<String> arrayList = new ArrayList<>();
        String currentWord;
        while (true) {
            currentWord = fileReader.getNextWord();
            if (currentWord == null) {
                break;
            }
            if (!currentWord.equals(EMPTY)) {
                arrayList.add(currentWord);
            }
        }
        String[] expectedResult = expected.split(" ");
        assertArrayEquals(expectedResult, arrayList.toArray());
    }
    public MyFileReader fileReader;
}
