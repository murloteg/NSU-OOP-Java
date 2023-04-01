package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.exceptions.FailedUserInterfaceInitializationException;
import ru.nsu.bolotov.view.InitializationChecker;
import ru.nsu.bolotov.view.gui.GraphicView;
import ru.nsu.bolotov.view.gui.GraphicInitializationChecker;
import ru.nsu.bolotov.sharedlogic.timer.TimerThread;
import ru.nsu.bolotov.view.text.TextDataGetter;
import ru.nsu.bolotov.view.text.TextInitializationChecker;
import ru.nsu.bolotov.view.text.TextView;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ApplicationController {
    private final Optional<GraphicView> graphicView;
    private final TextView textView;
    private final GameStarter gameStarter;
    private final TimerThread timerObject;

    public ApplicationController(Optional<GraphicView> graphicView, int fieldSize, int numberOfBombs) {
        this.graphicView = graphicView;
        gameStarter = new GameStarter(fieldSize, numberOfBombs);
        timerObject = new TimerThread();
        textView = graphicView.isEmpty() ? new TextView() : null;
    }

    public void executeGame() {
        initializationOfUserInterface();
        gameStarter.startGame();
        updateView();
        Thread timeThread = new Thread(timerObject);
        timeThread.start();
        // TODO: think about re-launch of game.
        while (!gameStarter.isEndOfGame()) {
            try {
                gameStarter.makeNextMove(getNextAction());
            } catch (FailedCreationException exception) {
                showInfoAboutExceptions("Move canceled");
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
            textView.showField(gameStarter.getUserField(), gameStarter.getLogicField());
            textView.showCurrentTime(timerObject.getCurrentTime());
        } else {
            graphicView.get().updateGameField(gameStarter.getUserField(), gameStarter.getLogicField(), timerObject.getCurrentTime());
        }
    }

    private void showInfoAboutExceptions(String message) {
        if (graphicView.isEmpty()) {
            textView.displayExceptionInfo(message);
        } else {
            graphicView.get().displayExceptionInfo(message);
        }
    }

    private void showGameStatus() {
        boolean defeatCondition = gameStarter.isAnyBombDetonated();
        if (graphicView.isEmpty()) {
            textView.displayGameStatus(defeatCondition, gameStarter.getFieldSize(), gameStarter.getNumberOfBombs(), timerObject.getCurrentTime());
        } else {
            graphicView.get().displayGameStatus(defeatCondition, gameStarter.getUserField(), gameStarter.getLogicField(), timerObject.getCurrentTime());
        }
    }

    private void initializationOfUserInterface() {
        InitializationChecker initializationChecker;
        if (graphicView.isEmpty()) {
            textView.printAvailableOptions();
            initializationChecker = new TextInitializationChecker(textView);
        } else {
            initializationChecker = new GraphicInitializationChecker(graphicView.get());
        }

        Thread initializationCheckerThread = new Thread(initializationChecker);
        initializationCheckerThread.start();
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                if (initializationChecker instanceof TextInitializationChecker) {
                    String option = TextDataGetter.chooseOption();
                    switch (option) {
                        case "NEW GAME": {
                            textView.setGameLaunched(true);
                            break;
                        }
                        case "ABOUT": {
                            textView.showGameRules();
                            break;
                        }
                        case "HIGH SCORES": {
                            textView.showHighScores();
                            break;
                        }
                    }
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new FailedUserInterfaceInitializationException();
            }
            if (initializationChecker.getInitializationStatus()) {
                initializationCheckerThread.interrupt();
                return;
            }
        }
    }
}
