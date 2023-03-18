package ru.nsu.bolotov.sharedlogic.field;

import ru.nsu.bolotov.util.UtilConsts;

import java.util.Arrays;

public class Field {
    private final int fieldSize;
    private final int numberOfBombs;
    private final int[] arrayOfFieldCells;

    public Field(int fieldSize, int numberOfBombs) {
        this.fieldSize = fieldSize;
        this.numberOfBombs = numberOfBombs;
        arrayOfFieldCells = new int[fieldSize * fieldSize];
        prepareArrayOfCellsForGame();
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int[] getArrayOfFieldCells() {
        return arrayOfFieldCells;
    }

    private void prepareArrayOfCellsForGame() {
        Arrays.fill(arrayOfFieldCells, UtilConsts.StatusesOfCells.EMPTY);
    }
}
