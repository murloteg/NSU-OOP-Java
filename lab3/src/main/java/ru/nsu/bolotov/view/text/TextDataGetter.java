package ru.nsu.bolotov.view.text;

import ru.nsu.bolotov.util.UtilConsts;

import java.util.Scanner;

public final class TextDataGetter {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputNextActionAsString() {
        return scanner.nextLine();
    }

    public static String chooseOption() {
        String option = scanner.nextLine().toUpperCase();
        while (true) {
            if (option.contains("NEW GAME")) {
                return "NEW GAME";
            } else if (option.contains("ABOUT")) {
                return "ABOUT";
            } else if (option.contains("HIGH SCORES")) {
                return "HIGH SCORES";
            } else {
                option = scanner.nextLine().toUpperCase();
            }
        }
    }

    private TextDataGetter() {
        throw new IllegalStateException(UtilConsts.StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
