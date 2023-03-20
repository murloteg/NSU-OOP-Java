package ru.nsu.bolotov.application;

import ru.nsu.bolotov.controller.ApplicationController;
import ru.nsu.bolotov.gui.GraphicView;

public class Main {
    public static void main(String[] args) {
//        GraphicView.showField();
        ApplicationController controller = new ApplicationController(1, 7, 6);
        controller.executeGame();
    }
}
