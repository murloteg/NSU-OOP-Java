package ru.nsu.bolotov.text;

import ru.nsu.bolotov.util.UtilConsts;
import ru.nsu.bolotov.sharedlogic.field.Field;

public final class TextView {
    public static void showField(Field userfField, Field logicField) {
        int fieldSize = userfField.getFieldSize();
        int[] userArrayOfFieldCells = userfField.getArrayOfFieldCells();
        int[] logicArrayOfFieldCells = logicField.getArrayOfFieldCells();
        printBorder(fieldSize);
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                if (j == 0) {
                    if (i < UtilConsts.TextViewConsts.MAX_HEX_FIELD_SIZE) {
                        System.out.print(Integer.toHexString(i) + "| ");
                    } else {
                        System.out.print(i + "| ");
                    }
                }
                int position = i * fieldSize + j;
                if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.OPEN) {
                    if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.BOMB) {
                        System.out.print("X ");
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.VISITED_EMPTY) {
                        System.out.print("0 ");
                    } else {
                        System.out.print(logicArrayOfFieldCells[position] + " ");
                    }
                } else if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FLAGGED) {
                    System.out.print("F ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("***** [END OF TURN] *****");
    }

    public static void showCurrentTime(long currentTime) {
        System.out.println("<=====> TIME <=====>");
        System.out.println("       " + currentTime + " sec   ");
        System.out.println();
    }

    public static void printAboutDefeat(long currentTime) {
        System.out.println("!!!>>>  DEFEAT  <<<!!!");
        printTotalScore(currentTime);
    }

    public static void printAboutVictory(long currentTime) {
        System.out.println("!!!>>>  VICTORY  <<<!!!");
        printTotalScore(currentTime);
    }

    private static void printTotalScore(long currentTime) {
        System.out.println("<===> TOTAL SCORE <===>");
        System.out.println("      " + currentTime * 10 + " points  "); // TODO: probably, create a new class for it.
        System.out.println();
    }

    private static void printBorder(int fieldSize) {
        System.out.print("   ");
        for (int i = 0; i < fieldSize; ++i) {
            if (i < UtilConsts.TextViewConsts.MAX_HEX_FIELD_SIZE) {
                System.out.print(Integer.toHexString(i) + " ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private TextView() {
        throw new IllegalStateException(UtilConsts.StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
