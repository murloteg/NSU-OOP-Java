package ru.nsu.bolotov.exceptions;

public class InvalidFieldPositionException extends IllegalArgumentException {
    private final int x;
    private final int y;
    private final int fieldSize;

    public InvalidFieldPositionException(int x, int y, int fieldSize) {
        this.x = x;
        this.y = y;
        this.fieldSize = fieldSize;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid position: (%d ; %d), because field size is: %d", x, y, fieldSize);
    }
}
