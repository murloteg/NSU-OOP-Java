package ru.nsu.bolotov.sharedlogic.scores;

import ru.nsu.bolotov.exceptions.EmptyPropertiesFile;
import ru.nsu.bolotov.exceptions.IOBusinessException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class HighScoresFinder {
    private static final InputStream PROPERTIES_FILE;
    private static final Properties PROPERTIES;

    static {
        try {
            PROPERTIES_FILE = new FileInputStream("/Users/mac/IntelliJProjects/Java-Projects/lab3/src/main/resources/highscores.properties");
        } catch (FileNotFoundException exception) {
            throw new IOBusinessException(exception);
        }
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(PROPERTIES_FILE);
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
        if (PROPERTIES.isEmpty()) {
            throw new EmptyPropertiesFile();
        }
        try {
            PROPERTIES_FILE.close();
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
    }

    public static String findPath(String alias) {
        return PROPERTIES.getProperty(alias);
    }

    private HighScoresFinder() {
        throw new IllegalStateException("Instantiation of high scores finder class");
    }
}
