package ru.nsu.bolotov.text;

import ru.nsu.bolotov.util.UtilConsts;

import java.util.Scanner;

public final class TextDataGetter {
    public static String getNextActionAsString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private TextDataGetter() {
        throw new IllegalStateException(UtilConsts.StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
