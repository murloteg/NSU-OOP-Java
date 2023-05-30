package ru.nsu.bolotov.parser;

import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.utils.UtilConsts;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationParser {
    private static final short PORT;
    private static final boolean LOGGING_STATUS;

    static {
        Properties properties = new Properties();
        Path configPath = Paths.get("config.properties");
        try (BufferedReader bufferedReader = Files.newBufferedReader(configPath)) {
            properties.load(bufferedReader);
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
        PORT = Short.parseShort(properties.getProperty("PORT"));
        LOGGING_STATUS = Boolean.parseBoolean(properties.getProperty("LOGGING_STATUS"));
    }

    public static short getPort() {
        return PORT;
    }

    public static boolean getLoggingStatus() {
        return LOGGING_STATUS;
    }

    private ConfigurationParser() {
        throw new IllegalStateException(UtilConsts.StringConsts.INSTANTIATION_MESSAGE);
    }
}
