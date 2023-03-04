package ru.nsu.bolotov.storageunit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class StorageUnitTest {
    @ParameterizedTest
    @CsvSource(value = {
            "sample : 4 : 9 : 44.4444",
            "test : 10 : 11 : 90.9091",
            "another : 1 : 100 : 1",
            "тест : 8 : 8 : 100",
            "123 : 123 : 124 : 99.1935",
            "oops : 5 : 10 : 50"
    }, delimiter = ':')
    void generalMethodsTest(String word, int frequency, int totalWordCounter, double expectedRatio) {
        StorageUnit storageUnit = new StorageUnit(word, frequency, totalWordCounter);
        assertEquals(word, storageUnit.getWord());
        assertEquals(Integer.toString(frequency), storageUnit.getFrequency());
        assertEquals(String.format("%.4f", expectedRatio) + "%", storageUnit.getRatioInPercents());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "sample : 4 : 9",
            "test : 10 : 11",
            "another : 1 : 100",
            "тест : 8 : 8 : 100",
            "123 : 123 : 124",
            "oops : 5 : 10"
    }, delimiter = ':')
    void compareTest(String word, int frequency, int totalWordCounter) {
        StorageUnit firstObject = new StorageUnit(word, frequency, totalWordCounter);
        StorageUnit similarObject = new StorageUnit(word, frequency, totalWordCounter);
        StorageUnit differentObject = new StorageUnit(word, frequency + 1, totalWordCounter);
        assertEquals(0, firstObject.compareTo(similarObject));
        assertNotEquals(0, firstObject.compareTo(differentObject));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "sample : 4 : 9",
            "test : 10 : 11",
            "another : 1 : 100",
            "тест : 8 : 8 : 100",
            "123 : 123 : 124",
            "oops : 5 : 10"
    }, delimiter = ':')
    void reflexiveEqualsTest(String word, int frequency, int totalWordCounter) {
        StorageUnit object = new StorageUnit(word, frequency, totalWordCounter);
        assertEquals(object, object);
    }
}
