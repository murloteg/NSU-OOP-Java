package ru.nsu.bolotov.sharedlogic.factory;

import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.exceptions.EmptyPropertiesFile;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.sharedlogic.action.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public final class ActionFactory {
    private static final InputStream PROPERTY_FILE;
    private static final Properties PROPERTIES;

    static {
        PROPERTY_FILE = Action.class.getClassLoader().getResourceAsStream("actions.properties");
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

    public static Action create(String actionType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> currentAction;
        try {
            currentAction = Class.forName(PROPERTIES.getProperty(actionType));
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException();
        }

        Annotation[] annotations = currentAction.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == ActionAnnotation.class) {
                Object createdAction = currentAction.getDeclaredConstructor().newInstance();
                if (createdAction instanceof Action) {
                    return (Action) createdAction;
                }
            }
        }
        throw new FailedCreationException();
    }

    private ActionFactory() {
        throw new IllegalStateException("Instantiation of factory class");
    }
}
