package ru.nsu.bolotov.exceptions;

public class TryingToOpenFlaggedCellException extends GameException {
    private final int x;
    private final int y;

    public TryingToOpenFlaggedCellException(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getMessage() {
        return String.format(":::: CELL (%d ; %d) FLAGGED. CLEAR IT, BEFORE OPENING ::::", x, y);
    }
}
