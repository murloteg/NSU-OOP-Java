package ru.nsu.bolotov.application.info;

public class UtilityInfo {
    public static void printInfo() {
        System.out.println(info);
    }
    private final static String info = "This program has only one argument: " +
            "input file name [String].\n" + "Provided file-extensions:\n" +
            "\t1) .txt\n" +
            "\t2) .doc\n" +
            "\t3) <empty>";
}
