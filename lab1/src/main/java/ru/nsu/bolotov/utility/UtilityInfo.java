package ru.nsu.bolotov.utility;

public abstract class UtilityInfo {
    private static final String INFO = "This program has only one argument: " +
            "input file name [String].\n" + "Provided file-extensions:\n" +
            "\t1) .txt\n" +
            "\t2) .doc\n" +
            "\t3) <empty>";


    public static void printInfo() {
        System.out.println(INFO);
    }

    private UtilityInfo() {
        throw new IllegalStateException("Utility class");
    }

}
