package ru.nsu.bolotov.util;

import ru.nsu.bolotov.exceptions.IOBusinessException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ImagePathFinder {
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        Path path = Paths.get("images.properties");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            PROPERTIES.load(reader);
        } catch (IOException exception) {
            throw new IOBusinessException();
        }
    }

    public static String getImagePath(String shortImageName) {
        return PROPERTIES.getProperty(shortImageName);
    }

    private ImagePathFinder() {
        throw new IllegalStateException(UtilConsts.StringConsts.INSTANTIATION_MESSAGE);
    }
}
