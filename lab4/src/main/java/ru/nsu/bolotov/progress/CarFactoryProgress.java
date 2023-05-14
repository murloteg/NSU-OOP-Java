package ru.nsu.bolotov.progress;

import ru.nsu.bolotov.car.CarIDSetter;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;

public class CarFactoryProgress {
    private final TaskQueue taskQueue;
    private final CarStorage carStorage;

    public CarFactoryProgress(TaskQueue taskQueue, CarStorage carStorage) {
        this.taskQueue = taskQueue;
        this.carStorage = carStorage;
    }

    public int getNumberOfTasksInQueue() {
        return taskQueue.getSize();
    }

    public int getTaskQueueLimit() {
        return taskQueue.getQueueLimit();
    }

    public int getNumberOfCarsInStorage() {
        return carStorage.getSize();
    }

    public int getCarStorageLimit() {
        return carStorage.getLimit();
    }

    public int getNumberOfCreatedCars() {
        return CarIDSetter.getNumberOfCreatedCars();
    }
}
