package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.gui.GraphicView;
import ru.nsu.bolotov.sharedlogic.timer.TimerThread;
import ru.nsu.bolotov.text.TextDataGetter;
import ru.nsu.bolotov.text.TextView;

import java.util.Optional;

public class ApplicationController {
    private final Optional<GraphicView> graphicView;
    private final GameStarter gameStarter;
    private final TimerThread timerObject;

    public ApplicationController(Optional<GraphicView> graphicView, int fieldSize, int numberOfBombs) {
        this.graphicView = graphicView;
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
                showInfoAboutExceptions(exception.getMessage());
            }
            updateView();
        }
        showGameStatus();
        thread.interrupt();
    }

    private String[] getNextAction() {
        if (graphicView.isEmpty()) {
            String nextActionAsString = TextDataGetter.inputNextActionAsString();
            String[] nextAction = nextActionAsString.split(" ");
            nextAction[0] = nextAction[0].toUpperCase();
            return nextAction;
        } else {  // TODO: add else branch.

        }

        return null; // FIXME: delete it.
    }

    private void updateView() {
        if (graphicView.isEmpty()) {
            TextView.showField(gameStarter.getUserField(), gameStarter.getLogicField());
            TextView.showCurrentTime(timerObject.getCurrentTime());
        } else {  // TODO: add else branch.

        }
    }

    private void showInfoAboutExceptions(String message) {
        if (graphicView.isEmpty()) {
            System.out.println(message);
        } else {  // TODO: add else branch.

        }
    }

    private void showGameStatus() {
        if (graphicView.isEmpty()) {
            if (gameStarter.isAnyBombDetonated()) {
                TextView.printAboutDefeat(timerObject.getCurrentTime());
            } else {
                TextView.printAboutVictory(timerObject.getCurrentTime());
            }
        } else {  // TODO: add else branch.

        }
    }
}
