package ru.nsu.bolotov.application;


import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.threadpool.actors.Dealer;
import ru.nsu.bolotov.threadpool.actors.Supplier;
import ru.nsu.bolotov.threadpool.actors.Worker;
import ru.nsu.bolotov.threadpool.controller.StorageController;
import ru.nsu.bolotov.threadpool.tasks.TaskQueue;

public class Main {
    public static void main(String[] args) {
        ComponentStorage<Component> engines = new ComponentStorage<>(1, 15);
        ComponentStorage<Component> wheels = new ComponentStorage<>(4, 30);
        ComponentStorage<Component> carcasses = new ComponentStorage<>(1, 8);

        Supplier supplier1 = new Supplier(engines, 500, "ENGINE");
        Supplier supplier2 = new Supplier(wheels, 350, "WHEEL");
        Supplier supplier3 = new Supplier(carcasses, 500, "CARCASS");

        Thread threadSup1 = new Thread(supplier1);
        Thread threadSup2 = new Thread(supplier2);
        Thread threadSup3 = new Thread(supplier3);

        threadSup1.start();
        threadSup2.start();
        threadSup3.start();

        CarStorage cars = new CarStorage(5);
        Dealer dealer = new Dealer(cars, 3000, true);

        Thread threadDealer1 = new Thread(dealer);
        Thread threadDealer2 = new Thread(dealer);
        threadDealer1.start();
        threadDealer2.start();

        TaskQueue taskQueue = new TaskQueue(5);

        StorageController storageController = new StorageController(taskQueue, carcasses, engines, wheels, cars, true);
        cars.addPropertyChangeListener(storageController);

        Thread controller = new Thread(storageController);
        controller.start();

        Worker worker1 = new Worker(taskQueue);
        Worker worker2 = new Worker(taskQueue);

        Thread threadWork1 = new Thread(worker1);
        Thread threadWork2 = new Thread(worker2);
        threadWork1.start();
        threadWork2.start();
    }
}
