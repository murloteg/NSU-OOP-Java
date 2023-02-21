package ru.nsu.bolotov.file;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.nsu.bolotov.utility.UtilityStringConsts.EMPTY;

class MyFileReaderTest {
    MyFileReaderTest() throws FileNotFoundException {
    }
    @Test
    void getNextWordTest() throws IOException {
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
        String[] expectedResult = new String[] {"this", "is", "simple", "test", "case", "end"};
        assertArrayEquals(expectedResult, arrayList.toArray());
    }
    private final MyFileReader fileReader = new MyFileReader("test-input");
}
