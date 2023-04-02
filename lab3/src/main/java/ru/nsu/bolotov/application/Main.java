package ru.nsu.bolotov.application;

import ru.nsu.bolotov.controller.ApplicationController;
import ru.nsu.bolotov.sharedlogic.scores.HighScoresSorter;
import ru.nsu.bolotov.view.gui.GraphicView;
import ru.nsu.bolotov.util.UtilConsts;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ApplicationStarter appStarter = new ApplicationStarter(args);
        appStarter.prepareArgs();
        int fieldSize = appStarter.getFieldSize();
        int numberOfBombs = appStarter.getNumberOfBombs();

        GraphicView graphicView = null;
        if (appStarter.getTypeOfView() == UtilConsts.ViewTypes.GRAPHIC_INTERFACE) {
            graphicView = new GraphicView(fieldSize, numberOfBombs);
        }
        ApplicationController controller = new ApplicationController(Optional.ofNullable(graphicView), fieldSize, numberOfBombs);
        controller.executeGame();
    }
}
