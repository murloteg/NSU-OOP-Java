package ru.nsu.bolotov.exceptions;

public class AttemptToUseOpenCellException extends GameException {
    private final int x;
    private final int y;

    public AttemptToUseOpenCellException(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getMessage() {
        return String.format(":::: CELL (%d ; %d) ALREADY OPEN ::::", x, y);
    }
}
