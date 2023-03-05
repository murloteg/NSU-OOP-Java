package ru.nsu.bolotov.container;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @ParameterizedTest
    @CsvSource(value = {
            "test : 1 : 1",
            "another : 1 : 2",
            "one : 1 : 3",
            "another : 2 : 4"
    }, delimiter = ':')
    void generalMethodsTest(String word, int expectedCounter, int expectedTotal) {
        container.addWordToMap(word);
        Map<String, Integer> dataMap = container.getContainer();
        assertTrue(dataMap.containsKey(word));
        assertEquals(expectedCounter, dataMap.get(word));
        assertInstanceOf(HashMap.class, dataMap);
        assertEquals(expectedTotal, container.getTotalWordCounter());
    }
    private static final Container container = new Container();
}
