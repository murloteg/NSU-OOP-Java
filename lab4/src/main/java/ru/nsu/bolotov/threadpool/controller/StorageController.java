package ru.nsu.bolotov.threadpool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.exceptions.BusinessInvalidArgumentException;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.threadpool.tasks.BuildTask;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StorageController implements PropertyChangeListener, Runnable {
    private final TaskQueue taskQueue;
    private final CarStorage cars;
    private final ComponentStorage<Component> carcasses;
    private final ComponentStorage<Component> engines;
    private final ComponentStorage<Component> accessories;
    private boolean isSleepingProcess;
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);
    private final boolean loggingStatus;

    public StorageController(TaskQueue taskQueue, ComponentStorage<Component> carcasses,
                             ComponentStorage<Component> engines, ComponentStorage<Component> accessories,
                             CarStorage cars, boolean loggingStatus) {
        this.taskQueue = taskQueue;
        this.carcasses = carcasses;
        this.engines = engines;
        this.accessories = accessories;
        this.cars = cars;
        this.loggingStatus = loggingStatus;
        isSleepingProcess = false;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!isSleepingProcess) {
                synchronized (taskQueue) {
                    int numberOfRequiredTasks = findNumberOfRequiredTasks();
                    for (int i = 0; i < numberOfRequiredTasks; ++i) {
                        taskQueue.addTask(new BuildTask(carcasses, engines, accessories, cars));
                    }
                    if (loggingStatus) {
                        LOGGER.info("Storage controller added {} tasks to queue.", numberOfRequiredTasks);
                    }
                    taskQueue.notifyAll();
                }
                isSleepingProcess = true;
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

    private int findNumberOfRequiredTasks() {
        float occupancyOfStorage = calculateStorageOccupied();
        validateValueOfOccupied(occupancyOfStorage);

        float occupancyOfQueue = calculateTaskQueueOccupied();
        validateValueOfOccupied(occupancyOfQueue);
        int predictableNumber;
        if (cars.getLimit() < taskQueue.getQueueLimit()) {
            predictableNumber = Math.round(cars.getLimit() * (1 - occupancyOfStorage));
        } else {
            predictableNumber = Math.round(taskQueue.getQueueLimit() * (1 - occupancyOfQueue));
        }
        return predictableNumber;
    }

    private float calculateStorageOccupied() {
        return (float) cars.getSize() / cars.getLimit();
    }

    private float calculateTaskQueueOccupied() {
        float valueOfQueueOccupancy = (float) taskQueue.getSize() / taskQueue.getQueueLimit();
        if (valueOfQueueOccupancy > 1) {
            valueOfQueueOccupancy = 1;
        }
        return valueOfQueueOccupancy;
    }

    private void validateValueOfOccupied(float valueOfOccupancy) {
        if (valueOfOccupancy < 0 || valueOfOccupancy > 1) {
            throw new BusinessInvalidArgumentException(0, 1, valueOfOccupancy);
        }
    }
}
