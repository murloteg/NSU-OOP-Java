package ru.nsu.bolotov.exceptions;

public class TryingToClearCellWithoutFlagException extends GameException {
    private final int x;
    private final int y;

    public TryingToClearCellWithoutFlagException(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getMessage() {
        return String.format(":::: CELL (%d ; %d) DOESN'T FLAGGED ::::", x, y);
    }
}
