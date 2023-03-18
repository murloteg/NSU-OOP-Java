package ru.nsu.bolotov.application;

import ru.nsu.bolotov.controller.ApplicationController;

public class Main {
    public static void main(String[] args) {
        ApplicationController controller = new ApplicationController(1, 10, 9);
        controller.executeGame();
    }
}
