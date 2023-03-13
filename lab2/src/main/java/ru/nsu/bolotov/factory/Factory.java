package ru.nsu.bolotov.factory;

import ru.nsu.bolotov.commands.operations.Command;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.exceptions.IncorrectPropertyFile;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;
import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import javax.annotation.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Properties;

public final class Factory {
    private static final String PATH = "all.properties";

    public static Command create(@Nonnull String command) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream resourceStream = Command.class.getClassLoader().getResourceAsStream(PATH);
        properties.load(resourceStream);
        if (properties.isEmpty()) {
            throw new IncorrectPropertyFile();
        }

        Class<?> currentCommandClass;
        try {
            currentCommandClass = Class.forName(properties.getProperty(command));
        } catch (RuntimeException exception) {
            throw new InvalidTypeOfArgumentException(new String[] {command});
        }
        Annotation[] annotations = currentCommandClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == CommandAnnotation.class) {
                return (Command) currentCommandClass.newInstance();
            }
        }
        throw new FailedCreationException();
    }

    private Factory() {
        throw new IllegalStateException("Instantiation of factory class");
    }
}
