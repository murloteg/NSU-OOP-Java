package ru.nsu.bolotov.factory;

import java.io.*;
import java.util.Properties;
import ru.nsu.bolotov.exceptions.IncorrectPropertyFile;
import ru.nsu.bolotov.exceptions.InvalidInstanceOfException;

import ru.nsu.bolotov.commands.*;

public class Factory {
    private Factory() {

    }
    public static Command create(String command) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream resourceStream = Command.class.getClassLoader().getResourceAsStream("all.properties");
        properties.load(resourceStream);
        if (properties.isEmpty()) {
            throw new IncorrectPropertyFile();
        }

        switch (command) {
            case "POP": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Pop;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Pop) instancedObject;
            }
            case "PUSH": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Push;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Push) instancedObject;
            }
            case "DEFINE": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Define;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Define) instancedObject;
            }
            case "PRINT": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Print;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Print) instancedObject;

            }
            case "PLUS": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Plus;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Plus) instancedObject;
            }
            case "MINUS": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Minus;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Minus) instancedObject;
            }
            case "MULTIPLY": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Multiply;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Multiply) instancedObject;
            }
            case "DIVIDE": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Divide;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Divide) instancedObject;
            }
            case "SQRT": {
                Class<?> currentCommandClass = Class.forName(properties.getProperty(command));
                Object instancedObject = currentCommandClass.newInstance();
                boolean resultOfInstanceOf = instancedObject instanceof Sqrt;
                if (!resultOfInstanceOf) {
                    throw new InvalidInstanceOfException();
                }
                return (Sqrt) instancedObject;
            }
            default:
                return null;
        }
    }
}
