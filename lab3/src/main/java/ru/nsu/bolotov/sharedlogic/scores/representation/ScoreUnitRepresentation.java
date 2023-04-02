package ru.nsu.bolotov.sharedlogic.scores.representation;

import ru.nsu.bolotov.exceptions.IncorrectScoreStringException;

public class ScoreUnitRepresentation implements Comparable<ScoreUnitRepresentation> {
    private final String scoreString;
    private double score;

    public ScoreUnitRepresentation(String scoreString) {
        this.scoreString = scoreString;
        findScoreInString();
    }

    public String getScoreString() {
        return scoreString;
    }

    @Override
    public int compareTo(ScoreUnitRepresentation other) {
        return Double.compare(this.score, other.score);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private void findScoreInString() {
        String findingRegion;
        if (scoreString.contains("GUI")) {
            findingRegion = "GUI, ";
        } else if (scoreString.contains("TEXT")) {
            findingRegion = "TEXT, ";
        } else {
            throw new IncorrectScoreStringException(scoreString);
        }
        String substringWithScore = scoreString.substring(scoreString.lastIndexOf(findingRegion) + findingRegion.length());
        score = Double.parseDouble(substringWithScore);
    }
}
