package ru.nsu.bolotov.commands.operations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private final Command comment = new Comment();

    @Test
    void commentExecuteTest() {
        assertDoesNotThrow(() -> {
            comment.execute(null, null);
        });
    }

    @Test
    void toStringTest() {
        assertEquals("COMMENT", comment.toString());
    }
}
