package ru.nsu.bolotov.controller;

import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.sharedlogic.action.Action;
import ru.nsu.bolotov.sharedlogic.factory.ActionFactory;
import ru.nsu.bolotov.sharedlogic.field.Field;
import ru.nsu.bolotov.sharedlogic.field.FillerOfLogicField;
import ru.nsu.bolotov.sharedlogic.field.FillerOfUserField;
import ru.nsu.bolotov.util.UtilConsts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStarter {
    private final int fieldSize;
    private final int numberOfBombs;
    private int[] indicesOfBombs;
    private Field logicField;
    private Field userField;
    private static final Logger GAME_LOGGER = LoggerFactory.getLogger(GameStarter.class);

    public GameStarter(int fieldSize, int numberOfBombs) {
        this.fieldSize = fieldSize;
        this.numberOfBombs = numberOfBombs;
    }

    public void startGame() {
        logicField = new Field(fieldSize, numberOfBombs);
        FillerOfLogicField.fillTheField(logicField);
        userField = new Field(fieldSize, numberOfBombs);
        FillerOfUserField.fillTheField(userField);
        getInfoAboutBombsConfiguration();
        GAME_LOGGER.trace("Game field was initialized successfully.");
    }

    public void makeNextMove(String[] nextActionRepresentation) {
        Action action;
        try {
            action = ActionFactory.create(nextActionRepresentation[0]);
        } catch (Exception exception) {
            throw new FailedCreationException();
        }

        ArrayList<Object> args = new ArrayList<>();
        Collections.addAll(args, userField, logicField);
        args.addAll(Arrays.asList(nextActionRepresentation).subList(1, nextActionRepresentation.length));
        action.doAction(args);
        GAME_LOGGER.info("Execute command {}", action);
    }

    public boolean isEndOfGame() {
        if (isAnyBombDetonated()) {
            return true;
        }
        int counterOfOpenCells = 0;
        int[] arrayOfFieldCells = userField.getArrayOfFieldCells();
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                if (arrayOfFieldCells[i * fieldSize + j] == UtilConsts.StatusesOfCells.OPEN) {
                    ++counterOfOpenCells;
                }
            }
        }
        return (counterOfOpenCells == fieldSize * fieldSize - numberOfBombs);
    }

    public boolean isAnyBombDetonated() {
        int[] arrayOfFieldCells = userField.getArrayOfFieldCells();
        for (int index : indicesOfBombs) {
            if (arrayOfFieldCells[index] == UtilConsts.StatusesOfCells.OPEN) {
                return true;
            }
        }
        return false;
    }

    public Field getUserField() {
        return userField;
    }

    public Field getLogicField() {
        return logicField;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    private void getInfoAboutBombsConfiguration() {
        indicesOfBombs = new int[numberOfBombs];
        int[] arrayOfFieldCells = logicField.getArrayOfFieldCells();
        int counter = 0;
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                if (arrayOfFieldCells[i * fieldSize + j] == UtilConsts.StatusesOfCells.BOMB) {
                    indicesOfBombs[counter] = i * fieldSize + j;
                    ++counter;
                }
            }
        }
    }
}
