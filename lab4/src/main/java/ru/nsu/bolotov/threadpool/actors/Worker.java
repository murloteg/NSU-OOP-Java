package ru.nsu.bolotov.threadpool.actors;

import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.threadpool.tasks.Task;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;

import java.util.Optional;

@Actor
public class Worker implements Runnable {
    private final TaskQueue taskQueue;

    public Worker(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            executeTask();
        }
    }

    private void executeTask() {
        synchronized (taskQueue) {
            while (taskQueue.isEmpty()) {
                try {
                    System.out.println("Worker waiting some task...");
                    taskQueue.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            Optional<Task> task = getNextSuitableTask();
            task.ifPresent(Task::doWork);
        }
    }

    private Optional<Task> getNextSuitableTask() {
        return taskQueue.getTask("BUILD_TASK");
    }
}
