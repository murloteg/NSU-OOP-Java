package ru.nsu.bolotov.sharedlogic.field;

import ru.nsu.bolotov.util.UtilConsts;

public final class FillerOfUserField {
    public static void fillTheField(Field field) {
        int fieldSize = field.getFieldSize();
        int[] arrayOfFieldCells = field.getArrayOfFieldCells();
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                arrayOfFieldCells[i * fieldSize + j] = UtilConsts.StatusesOfCells.HIDDEN;
            }
        }
    }

    private FillerOfUserField() {
        throw new IllegalStateException(UtilConsts.StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
