package ru.nsu.bolotov.application;

import ru.nsu.bolotov.cmdlineparser.CommandLineParser;
import ru.nsu.bolotov.controller.CalculatorController;

import java.util.Objects;

import static ru.nsu.bolotov.util.UtilStringConsts.EMPTY;

public class Main {
    private static String path = EMPTY;

    public static void main(String[] args) {
        CommandLineParser.main(args);
        if (Objects.nonNull(CommandLineParser.inputPath)) {
            path = CommandLineParser.inputPath;
        }

        CalculatorController controller;
        if (path.isEmpty()) {
            controller = new CalculatorController();
        } else {
            controller = new CalculatorController(path);
        }
        controller.calculation();
    }
}
