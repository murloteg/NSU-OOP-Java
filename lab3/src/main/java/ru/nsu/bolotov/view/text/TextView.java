package ru.nsu.bolotov.view.text;

import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.sharedlogic.scores.HighScoresFinder;
import ru.nsu.bolotov.sharedlogic.scores.ScoreCalculator;
import ru.nsu.bolotov.util.UtilConsts;
import ru.nsu.bolotov.sharedlogic.field.Field;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextView {
    private boolean isGameLaunched;

    public TextView() {
        isGameLaunched = false;
    }

    public void setGameLaunched(boolean isGameLaunched) {
        this.isGameLaunched = isGameLaunched;
    }

    public boolean getStatusOfGameLaunch() {
        return isGameLaunched;
    }

    public void showField(Field userField, Field logicField) {
        int fieldSize = userField.getFieldSize();
        int[] userArrayOfFieldCells = userField.getArrayOfFieldCells();
        int[] logicArrayOfFieldCells = logicField.getArrayOfFieldCells();
        printBorder(fieldSize);
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                if (j == 0) {
                    if (i < UtilConsts.TextViewConsts.MAX_HEX_FIELD_SIZE) {
                        System.out.print(Integer.toHexString(i) + "| ");
                    } else {
                        System.out.print(i + "| ");
                    }
                }
                int position = i * fieldSize + j;
                if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.OPEN) {
                    if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.BOMB) {
                        System.out.print("X ");
                    } else if (logicArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.VISITED_EMPTY) {
                        System.out.print("0 ");
                    } else {
                        System.out.print(logicArrayOfFieldCells[position] + " ");
                    }
                } else if (userArrayOfFieldCells[position] == UtilConsts.StatusesOfCells.FLAGGED) {
                    System.out.print("F ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("***** [END OF TURN] *****");
    }

    public void showCurrentTime(long currentTime) {
        System.out.println("<=====> TIME <=====>");
        System.out.println("       " + currentTime + " sec   ");
        System.out.println();
    }

    public void displayExceptionInfo(String message) {
        System.out.println(message);
    }

    public void displayGameStatus(boolean defeatCondition, int fieldSize, int numberOfBombs, long currentTime) {
        if (defeatCondition) {
            printAboutDefeat();
        } else {
            printAboutVictory();
        }
        printTotalScore(fieldSize, numberOfBombs, currentTime);
    }

    public void showGameRules() {
        System.out.println("1) Your goal is to open all cells without bombs.");
        System.out.println("2) When the game starts, you can do only these actions:");
        System.out.println("\t\t\t OPEN THE CELL (x, y);");
        System.out.println("\t\t\t PUT THE FLAG (x, y);");
        System.out.println("\t\t\t CLEAR THE CELL FROM THE FLAG (x, y);");
        System.out.println("3) You must finish as fast as you can!");
    }

    public void printAvailableOptions() {
        System.out.println("CHOOSE OPTION: ");
        System.out.println("---> NEW GAME");
        System.out.println("---> ABOUT");
        System.out.println("---> HIGH SCORES");
    }

    public void showHighScores() {
        Path path = Paths.get(HighScoresFinder.findPath("PATH"));
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            printScores(bufferedReader);
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
    }

    private void printScores(BufferedReader bufferedReader) {
        try {
            String score = bufferedReader.readLine();
            while (score != null) {
                System.out.println(score);
                score = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
    }

    private void printAboutDefeat() {
        System.out.println("!!!>>>  DEFEAT  <<<!!!");

    }

    private void printAboutVictory() {
        System.out.println("!!!>>>  VICTORY  <<<!!!");
    }

    private void printTotalScore(int fieldSize, int numberOfBombs, long currentTime) {
        System.out.println("<===> TOTAL SCORE <===>");
        ScoreCalculator.calculateScore(fieldSize, numberOfBombs, currentTime);
        System.out.println("      " + ScoreCalculator.getScore() + " points  ");
        System.out.println();
    }

    private void printBorder(int fieldSize) {
        System.out.print("   ");
        for (int i = 0; i < fieldSize; ++i) {
            if (i < UtilConsts.TextViewConsts.MAX_HEX_FIELD_SIZE) {
                System.out.print(Integer.toHexString(i) + " ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
