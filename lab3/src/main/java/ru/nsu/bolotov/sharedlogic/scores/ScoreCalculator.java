package ru.nsu.bolotov.sharedlogic.scores;

public final class ScoreCalculator {
    private static double score;

    public static void calculateScore(int fieldSize, int numberOfBombs, long currentTime) {
        score = ((double) fieldSize * fieldSize / 2) + numberOfBombs * numberOfBombs * 1.5;
        score -= currentTime * 0.6;
        score = (score < 0) ? 0 : score;
    }

    public static double getScore() {
        return score;
    }

    private ScoreCalculator() {
        throw new IllegalStateException("Instantiation of score calculator class");
    }
}
