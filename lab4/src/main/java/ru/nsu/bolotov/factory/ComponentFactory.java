package ru.nsu.bolotov.factory;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.components.ComponentAnnotation;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.exceptions.PropertiesFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ComponentFactory {
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        Path propertiesPath = Paths.get("components.properties");
        try (BufferedReader bufferedReader = Files.newBufferedReader(propertiesPath)) {
            PROPERTIES.load(bufferedReader);
        } catch (IOException exception) {
            throw new IOBusinessException();
        }
    }

    public static Component createComponent(String componentType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> componentClass;
        try {
            componentClass = Class.forName(PROPERTIES.getProperty(componentType));
        } catch (ClassNotFoundException exception) {
            throw new PropertiesFileException();
        }
        Annotation[] annotations = componentClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == ComponentAnnotation.class) {
                Object instancedObject = componentClass.getDeclaredConstructor().newInstance();
                if (instancedObject instanceof Component) {
                    return (Component) instancedObject;
                }
                break;
            }
        }
        throw new FailedCreationException();
    }

    private ComponentFactory() {
        throw new IllegalStateException("Instantiation of factory class");
    }
}
