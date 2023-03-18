package ru.nsu.bolotov.sharedlogic.field;

import ru.nsu.bolotov.util.UtilConsts;

import java.util.Random;

// TODO: think about filling of the first turn.
public final class FillerOfLogicField {
    private static final Random GENERATOR = new Random();
    
    public static void fillTheField(Field field) {
        int fieldSize = field.getFieldSize();
        int[] arrayOfFieldCells = field.getArrayOfFieldCells();
        fillWithMines(field);

        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                int numberOfMinedNeighbors = 0;
                int currentPosition = i * fieldSize + j;
                /* cell in the top left corner */
                if (currentPosition == 0) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition + 1,
                            currentPosition + fieldSize, currentPosition + fieldSize + 1);
                /* cell in the top right corner */
                } else if (currentPosition == (fieldSize - 1)) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition - 1,
                            currentPosition + fieldSize, currentPosition + fieldSize - 1);
                /* cell in the lower left corner */
                } else if (currentPosition == ((fieldSize - 1) * fieldSize)) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition + 1,
                            currentPosition - fieldSize, currentPosition - fieldSize + 1);
                /* cell in the lower right corner */
                } else if (currentPosition == (fieldSize * fieldSize - 1)) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition - 1,
                            currentPosition - fieldSize, currentPosition - fieldSize - 1);
                /* cell in the top line [excluding handled cases] */
                } else if (currentPosition > 0 && currentPosition < fieldSize) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition - 1,
                            currentPosition + 1, currentPosition + fieldSize - 1,
                            currentPosition + fieldSize, currentPosition + fieldSize + 1);
                /* cell in the lower line [excluding handled cases] */
                } else if (currentPosition > ((fieldSize - 1) * fieldSize) && currentPosition < (fieldSize * fieldSize - 1)) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition - 1,
                            currentPosition + 1, currentPosition - fieldSize - 1,
                            currentPosition - fieldSize, currentPosition - fieldSize + 1);
                /* cell in the left column [excluding handled cases] */
                } else if (currentPosition % fieldSize == 0) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition + 1,
                            currentPosition - fieldSize, currentPosition - fieldSize + 1,
                            currentPosition + fieldSize, currentPosition + fieldSize + 1);
                /* cell in the right column [excluding handled cases] */
                } else if (currentPosition % fieldSize == (fieldSize - 1)) {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition - 1,
                            currentPosition - fieldSize, currentPosition - fieldSize - 1,
                            currentPosition + fieldSize, currentPosition + fieldSize - 1);
                } else {
                    numberOfMinedNeighbors = getNumberOfMinedNeighbors(arrayOfFieldCells, currentPosition - 1,
                            currentPosition + 1, currentPosition - fieldSize - 1,
                            currentPosition - fieldSize, currentPosition - fieldSize + 1,
                            currentPosition + fieldSize - 1, currentPosition + fieldSize,
                            currentPosition + fieldSize + 1);
                }

                if (isThisCellMined(arrayOfFieldCells[currentPosition]) == UtilConsts.StatusesOfCells.EMPTY) {
                    putInfoAboutMinedNeighbors(arrayOfFieldCells, currentPosition, numberOfMinedNeighbors);
                }
            }
        }
    }

    private static void fillWithMines(Field field) {
        int fieldSize = field.getFieldSize();
        int numberOfBombs = field.getNumberOfBombs();
        int[] arrayOfFieldCells = field.getArrayOfFieldCells();

        int index = 0;
        int counterOfMines = 0;
        int randomValue = GENERATOR.nextInt(fieldSize);
        while (counterOfMines < numberOfBombs) {
            if (index >= fieldSize * fieldSize) {
                index %= fieldSize;
            }

            if ((arrayOfFieldCells[index] != UtilConsts.StatusesOfCells.BOMB) && (randomValue % UtilConsts.RandomConsts.REMAINDER == 0)) {
                ++counterOfMines;
                arrayOfFieldCells[index] = UtilConsts.StatusesOfCells.BOMB;
            }
            randomValue = GENERATOR.nextInt(fieldSize);
            ++index;
        }
    }

    private static int isThisCellMined(int statusOfCell) {
        if (statusOfCell == UtilConsts.StatusesOfCells.BOMB) {
            return 1;
        }
        return 0;
    }

    private static int getNumberOfMinedNeighbors(int[] arrayOfFieldCells, int... indicesOfNeighbors) {
        int numberOfMinedNeighbors = 0;
        for (int index : indicesOfNeighbors) {
            numberOfMinedNeighbors += isThisCellMined(arrayOfFieldCells[index]);
        }
        return numberOfMinedNeighbors;
    }

    private static void putInfoAboutMinedNeighbors(int[] arrayOfFieldCells, int currentIndex, int numberOfMinedNeighbors) {
        arrayOfFieldCells[currentIndex] = numberOfMinedNeighbors;
    }

    private FillerOfLogicField() {
        throw new IllegalStateException(UtilConsts.StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
