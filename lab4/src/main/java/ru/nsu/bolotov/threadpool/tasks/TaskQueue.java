package ru.nsu.bolotov.threadpool.tasks;

import ru.nsu.bolotov.exceptions.IOBusinessException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Properties;

public class TaskQueue {
    private final LinkedList<Task> tasks;
    private final int queueLimit;
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        Path propertiesPath = Paths.get("tasks.properties");
        try (BufferedReader bufferedReader = Files.newBufferedReader(propertiesPath)) {
            PROPERTIES.load(bufferedReader);
        } catch (IOException exception) {
            throw new IOBusinessException();
        }
    }

    public TaskQueue(int queueLimit) {
        tasks = new LinkedList<>();
        this.queueLimit = queueLimit;
    }

    public Optional<Task> getTask(String taskType) {
        for (int i = 0; i < tasks.size(); ++i) {
            String fullTaskName = PROPERTIES.getProperty(taskType);
            if (tasks.get(i).getClass().getCanonicalName().equals(fullTaskName)) {
                return Optional.of(tasks.remove(i));
            }
        }
        return Optional.empty();
    }

    public void addTask(Task task) {
        tasks.addLast(task);
    }

    public int getSize() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int getQueueLimit() {
        return queueLimit;
    }
}
