package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.text.TextDataGetter;
import ru.nsu.bolotov.text.TextView;
import ru.nsu.bolotov.util.UtilConsts;

public class ApplicationController {
    private final int typeOfView;
    private final GameStarter gameStarter;

    public ApplicationController(int typeOfView) {
        this.typeOfView = typeOfView;
        gameStarter = new GameStarter();
    }

    public ApplicationController(int typeOfView, int fieldSize, int numberOfBombs) {
        this.typeOfView = typeOfView;
        gameStarter = new GameStarter(fieldSize, numberOfBombs);
    }

    public void executeGame() {
        gameStarter.startGame();
        updateView();
        while (!gameStarter.isEndOfGame()) {
            gameStarter.makeNextMove(getNextAction());
            updateView();
        }
        printGameStatus();
    }

    private String[] getNextAction() {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            String nextActionAsString = TextDataGetter.getNextActionAsString();
            String[] nextAction = nextActionAsString.split(" ");
            nextAction[0] = nextAction[0].toUpperCase();
            return nextAction;
        }

        return null; // FIXME: delete it.
    }

    private void updateView() {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            TextView.showField(gameStarter.getUserField(), gameStarter.getLogicField());
        }
    }

    private void printGameStatus() {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            if (gameStarter.isAnyBombDetonated()) {
                TextView.printAboutDefeat();
            } else {
                TextView.printAboutVictory();
            }
        } else {

        }
    }
}
