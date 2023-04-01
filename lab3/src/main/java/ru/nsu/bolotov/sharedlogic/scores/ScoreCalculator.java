package ru.nsu.bolotov.sharedlogic.scores;

public final class ScoreCalculator {
    private static double score;

    public static void calculateScore(int fieldSize, int numberOfBombs, long currentTime) {
        score = fieldSize * fieldSize + numberOfBombs * 3;
        score -= currentTime * 0.7;
    }

    public static double getScore() {
        return score;
    }

    private ScoreCalculator() {
        throw new IllegalStateException("Instantiation of score calculator class");
    }
}
