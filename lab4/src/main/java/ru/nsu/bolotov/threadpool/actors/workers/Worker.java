package ru.nsu.bolotov.threadpool.actors.workers;

import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.threadpool.actors.Actor;
import ru.nsu.bolotov.threadpool.tasks.BuildTask;
import ru.nsu.bolotov.threadpool.tasks.Task;

import java.util.List;
import java.util.Optional;

public class Worker implements Actor, Runnable {
    private final List<Task> tasks;

    public Worker(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            executeTask();
        }
    }

    private void executeTask() {
        synchronized (tasks) {
            while (tasks.isEmpty()) {
                try {
                    tasks.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            Optional<Task> task = findSuitableTask();
            task.ifPresent(Task::doWork);
        }
    }

    private Optional<Task> findSuitableTask() {
        for (int i = 0; i < tasks.size(); ++i) {
            if (tasks.get(i) instanceof BuildTask) {
                return Optional.of(tasks.remove(i));
            }
        }
        return Optional.empty();
    }
}
