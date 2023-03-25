package ru.nsu.bolotov.application;

import ru.nsu.bolotov.controller.ApplicationController;
import ru.nsu.bolotov.gui.GraphicView;
import ru.nsu.bolotov.util.UtilConsts;

import javax.swing.*;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ApplicationStarter starter = new ApplicationStarter(args);
        starter.prepareArgs();
        GraphicView graphicView = null;
        if (starter.getTypeOfView() == UtilConsts.ViewTypes.GRAPHIC_INTERFACE) {
            graphicView = new GraphicView();
        }
        ApplicationController controller = new ApplicationController(Optional.ofNullable(graphicView) , starter.getFieldSize(), starter.getNumberOfBombs());
        controller.executeGame();
    }
}
