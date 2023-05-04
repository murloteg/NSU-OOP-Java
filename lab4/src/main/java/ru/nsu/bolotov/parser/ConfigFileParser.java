package ru.nsu.bolotov.parser;

import ru.nsu.bolotov.exceptions.IOBusinessException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ConfigFileParser {
    private static int maxCarcassNumber;
    private static int maxEnginesNumber;
    private static int maxAccessoriesNumber;
    private static int accessoriesSuppliersNumber;
    private static int maxCarNumber;
    private static int workersNumber;
    private static int dealersNumber;
    private static boolean loggingStatus;

    static {
        Path path = Paths.get("config.properties");
        try (BufferedReader configFile = Files.newBufferedReader(path)) {
            Properties properties = new Properties();
            properties.load(configFile);
            parseParameters(properties);
        } catch (IOException exception) {
            throw new IOBusinessException();
        }
    }

    private static void parseParameters(Properties properties) {
        maxCarcassNumber = Integer.parseInt(properties.getProperty("MAX_CARCASSES_NUMBER"));
        maxEnginesNumber = Integer.parseInt(properties.getProperty("MAX_ENGINES_NUMBER"));
        maxAccessoriesNumber = Integer.parseInt(properties.getProperty("MAX_ACCESSORIES_NUMBER"));
        accessoriesSuppliersNumber = Integer.parseInt(properties.getProperty("ACCESSORIES_SUPPLIERS_NUMBER"));
        maxCarNumber = Integer.parseInt(properties.getProperty("MAX_CAR_NUMBER"));
        workersNumber = Integer.parseInt(properties.getProperty("WORKERS_NUMBER"));
        dealersNumber = Integer.parseInt(properties.getProperty("DEALERS_NUMBER"));
        loggingStatus = Boolean.parseBoolean(properties.getProperty("LOGGING_STATUS"));
    }

    public static int getMaxCarcassNumber() {
        return maxCarcassNumber;
    }

    public static int getMaxEnginesNumber() {
        return maxEnginesNumber;
    }

    public static int getMaxAccessoriesNumber() {
        return maxAccessoriesNumber;
    }

    public static int getAccessoriesSuppliersNumber() {
        return accessoriesSuppliersNumber;
    }
    public static int getMaxCarNumber() {
        return maxCarNumber;
    }

    public static int getWorkersNumber() {
        return workersNumber;
    }

    public static int getDealersNumber() {
        return dealersNumber;
    }

    public static boolean getLoggingStatus() {
        return loggingStatus;
    }

    private ConfigFileParser() {
        throw new IllegalStateException("Instantiation of parser class");
    }
}
