package ru.nsu.bolotov.factory;

import ru.nsu.bolotov.commands.operations.Command;
import ru.nsu.bolotov.commands.annotations.CommandAnnotation;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.exceptions.IncorrectPropertyFile;
import ru.nsu.bolotov.exceptions.InvalidTypeOfArgumentException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Properties;

public abstract class Factory {
    private Factory() {
        throw new IllegalStateException("Instantiation of abstract class");
    }

    public static Command create(String command) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream resourceStream = Command.class.getClassLoader().getResourceAsStream("all.properties");
        properties.load(resourceStream);
        if (properties.isEmpty()) {
            throw new IncorrectPropertyFile();
        }

        Class<?> currentCommandClass;
        try {
            currentCommandClass= Class.forName(properties.getProperty(command));
        } catch (RuntimeException exception) {
            throw new InvalidTypeOfArgumentException(new String[] {command});
        }
        Annotation[] annotations = currentCommandClass.getAnnotations();
        for (Annotation annotation: annotations) {
            if (annotation.annotationType() == CommandAnnotation.class) {
                return (Command) currentCommandClass.newInstance();
            }
        }
        throw new FailedCreationException();
    }
}
