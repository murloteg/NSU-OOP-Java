package ru.nsu.bolotov.sharedlogic.scores;

import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.sharedlogic.scores.representation.ScoreUnitRepresentation;
import ru.nsu.bolotov.util.UtilConsts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class HighScoresSorter {
    public static void sortHighScores() {
        List<ScoreUnitRepresentation> scores;
        Path path = Paths.get(HighScoresFinder.findPath("PATH"));
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
             scores = new ArrayList<>();
            String scoreString = bufferedReader.readLine();
            while (Objects.nonNull(scoreString)) {
                scores.add(new ScoreUnitRepresentation(scoreString));
                scoreString = bufferedReader.readLine();
            }
            scores.sort(Comparator.reverseOrder());
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
        overrideHighScoresFile(path, scores);
    }

    private static void overrideHighScoresFile(Path path, List<ScoreUnitRepresentation> scores) {

        try (FileWriter fileWriter = new FileWriter(path.toFile(), false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int counter = 0;
            while (counter < scores.size() && counter < UtilConsts.HighScoresConsts.MAX_LENGTH_OF_HIGH_SCORES_LIST) {
                bufferedWriter.write(scores.get(counter).getScoreString());
                bufferedWriter.newLine();
                ++counter;
            }
            bufferedWriter.close();
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
    }

    private HighScoresSorter() {
        throw new IllegalStateException("Instantiation of high score sorter class");
    }
}
