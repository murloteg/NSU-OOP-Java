package ru.nsu.bolotov.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorControllerTest {
    @Test
    void constructorWithoutArgsTest() {
        assertDoesNotThrow(() -> new CalculatorController());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "test-input-first.txt",
            "test-input-second.txt"
    })
    void constructorWithPathTest(String path) {
        assertDoesNotThrow(() -> new CalculatorController(path));
    }
}
