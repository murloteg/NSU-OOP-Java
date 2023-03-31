package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.gui.GraphicView;
import ru.nsu.bolotov.gui.GraphicInitializationChecker;
import ru.nsu.bolotov.sharedlogic.timer.TimerThread;
import ru.nsu.bolotov.text.TextDataGetter;
import ru.nsu.bolotov.text.TextView;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        initializationOfUserInterface();
        gameStarter.startGame();
        updateView();
        Thread timeThread = new Thread(timerObject);
        timeThread.start();
        while (!gameStarter.isEndOfGame()) {
            try {
                gameStarter.makeNextMove(getNextAction());
            } catch (RuntimeException exception) {
                showInfoAboutExceptions(exception.getMessage());
            }
            updateView();
        }
        showGameStatus();
        timeThread.interrupt();
    }

    private String[] getNextAction() {
        String nextActionAsString;
        if (graphicView.isEmpty()) {
            nextActionAsString = TextDataGetter.inputNextActionAsString();
            String[] nextAction = nextActionAsString.split(" ");
            nextAction[0] = nextAction[0].toUpperCase();
            return nextAction;
        } else {
            nextActionAsString = graphicView.get().getNextActionAsString();
            String[] nextAction = nextActionAsString.split(" ");
            nextAction[0] = nextAction[0].toUpperCase();
            return nextAction;
        }
    }

    private void updateView() {
        if (graphicView.isEmpty()) {
            TextView.showField(gameStarter.getUserField(), gameStarter.getLogicField());
            TextView.showCurrentTime(timerObject.getCurrentTime());
        } else {
            graphicView.get().updateGameField(gameStarter.getUserField(), gameStarter.getLogicField(), timerObject.getCurrentTime());
        }
    }

    private void showInfoAboutExceptions(String message) {
        if (graphicView.isEmpty()) {
            System.out.println(message);
        } else {
            graphicView.get().displayExceptionInfo(message);
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

    private void initializationOfUserInterface() {
        if (graphicView.isEmpty()) {

        } else {
            GraphicInitializationChecker initializationChecker = new GraphicInitializationChecker(graphicView.get());
            Thread initializationCheckerThread = new Thread(initializationChecker);
            initializationCheckerThread.start();
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e); // TODO: add custom exception.
                }
                if (initializationChecker.getInitializationStatus()) {
                    initializationCheckerThread.interrupt();
                    return;
                }
            }
        }
    }
}
