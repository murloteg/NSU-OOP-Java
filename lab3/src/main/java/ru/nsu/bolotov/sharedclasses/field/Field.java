package ru.nsu.bolotov.sharedclasses.field;

import ru.nsu.bolotov.util.UtilConsts;

import java.util.Arrays;

public class Field {
    private int fieldSize = UtilConsts.FieldConsts.DEFAULT_FIELD_SIZE;
    private int numberOfMines = UtilConsts.FieldConsts.DEFAULT_NUMBER_OF_MINES;
    private final int[] arrayOfFieldCells;

    public Field() {
        arrayOfFieldCells = new int[fieldSize * fieldSize];
        prepareArrayOfCellsForGame();
    }

    public Field(int fieldSize) {
        this.fieldSize = fieldSize;
        arrayOfFieldCells = new int[fieldSize * fieldSize];
        prepareArrayOfCellsForGame();
    }

    public Field(int fieldSize, int numberOfMines) {
        this.fieldSize = fieldSize;
        this.numberOfMines = numberOfMines;
        arrayOfFieldCells = new int[fieldSize * fieldSize];
        prepareArrayOfCellsForGame();
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int[] getArrayOfFieldCells() {
        return arrayOfFieldCells;
    }

    private void prepareArrayOfCellsForGame() {
        Arrays.fill(arrayOfFieldCells, UtilConsts.StatusesOfCells.EMPTY);
    }

    // FIXME: delete later.
    public void printField() {
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                System.out.print(arrayOfFieldCells[i * fieldSize + j] + " ");
            }
            System.out.println();
        }
    }
}
