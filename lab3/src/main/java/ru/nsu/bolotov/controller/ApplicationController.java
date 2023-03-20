package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.sharedlogic.timer.TimerThread;
import ru.nsu.bolotov.text.TextDataGetter;
import ru.nsu.bolotov.text.TextView;
import ru.nsu.bolotov.util.UtilConsts;

public class ApplicationController {
    private final int typeOfView;
    private final GameStarter gameStarter;
    private final TimerThread timerObject;

    public ApplicationController(int typeOfView) {
        this.typeOfView = typeOfView;
        gameStarter = new GameStarter();
        timerObject = new TimerThread();
    }

    public ApplicationController(int typeOfView, int fieldSize, int numberOfBombs) {
        this.typeOfView = typeOfView;
        gameStarter = new GameStarter(fieldSize, numberOfBombs);
        timerObject = new TimerThread();
    }

    public void executeGame() {
        Thread thread = new Thread(timerObject);
        thread.start();
        gameStarter.startGame();
        updateView();
        while (!gameStarter.isEndOfGame()) {
            try {
                gameStarter.makeNextMove(getNextAction());
            } catch (RuntimeException exception) {
                displayInfoAboutException(exception.getMessage());
            }
            updateView();
        }
        printGameStatus();
        thread.interrupt();
    }

    private String[] getNextAction() {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            String nextActionAsString = TextDataGetter.getNextActionAsString();
            String[] nextAction = nextActionAsString.split(" ");
            nextAction[0] = nextAction[0].toUpperCase();
            return nextAction;
        } else {  // TODO: add else branch.

        }

        return null; // FIXME: delete it.
    }

    private void updateView() {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            TextView.showField(gameStarter.getUserField(), gameStarter.getLogicField());
            TextView.showCurrentTime(timerObject.getCurrentTime());
        } else {  // TODO: add else branch.

        }
    }

    private void displayInfoAboutException(String message) {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            System.out.println(message);
        } else {  // TODO: add else branch.

        }
    }

    private void printGameStatus() {
        if (typeOfView == UtilConsts.ViewTypes.TEXT) {
            if (gameStarter.isAnyBombDetonated()) {
                TextView.printAboutDefeat(timerObject.getCurrentTime());
            } else {
                TextView.printAboutVictory(timerObject.getCurrentTime());
            }
        } else {  // TODO: add else branch.

        }
    }
}
