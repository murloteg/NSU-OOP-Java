package ru.nsu.bolotov.threadpool.controller;


import ru.nsu.bolotov.threadpool.tasks.Task;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

public class StorageController implements PropertyChangeListener, Runnable {
    private final TaskQueue taskQueue;
    private boolean isSleepingProcess;

    public StorageController(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
        isSleepingProcess = true;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!isSleepingProcess) {

            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        this.setProcessStatus((Boolean) event.getNewValue());
    }

    private void setProcessStatus(boolean sleepingStatus) {
        isSleepingProcess = sleepingStatus;
    }

    private int calculateNewBuildTasksNumber() {
        int numberOfBuildTasks = taskQueue.getNumberOfSpecifiedTasks("BUILD_TASK");

    }

    private int calculateNewReplenishmentTasksNumber() {
        int numberOfBuildTasks = taskQueue.getNumberOfSpecifiedTasks("REPLENISHMENT_TASK");

    }
}
