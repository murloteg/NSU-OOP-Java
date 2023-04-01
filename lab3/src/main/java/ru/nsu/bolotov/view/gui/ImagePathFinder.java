package ru.nsu.bolotov.view.gui;

import ru.nsu.bolotov.exceptions.EmptyPropertiesFile;
import ru.nsu.bolotov.exceptions.IOBusinessException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ImagePathFinder {
    private static final InputStream PROPERTY_FILE;
    private static final Properties PROPERTIES;

    static {
        try {
            PROPERTY_FILE = new FileInputStream("/Users/mac/IntelliJProjects/Java-Projects/lab3/src/main/resources/images.properties");
        } catch (FileNotFoundException exception) {
            throw new IOBusinessException(exception);
        }
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(PROPERTY_FILE);
        } catch (IOException exception) {
            throw new EmptyPropertiesFile();
        }
        if (PROPERTIES.isEmpty()) {
            throw new EmptyPropertiesFile();
        }
        try {
            PROPERTY_FILE.close();
        } catch (IOException exception) {
            throw new IOBusinessException(exception);
        }
    }

    public static String findPath(String imageName) {
        return PROPERTIES.getProperty(imageName.toUpperCase());
    }

    private ImagePathFinder() {
        throw new IllegalStateException("Instantiation of path finder class");
    }
}
