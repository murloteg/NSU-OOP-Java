package ru.nsu.bolotov.threadpool.tasks;

import ru.nsu.bolotov.car.Car;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.CarStorage;

/* it's probably useless task! */
public class SellTask implements Task {
    private final CarStorage cars;

    public SellTask(CarStorage cars) {
        this.cars = cars;
    }

    @Override
    public void doWork() {
        synchronized (cars) {
            while (cars.isEmpty()) {
                try {
                    cars.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            Car soldCar = cars.getCar();
            System.out.println("Sold car ID: " + soldCar);
        }
    }
}
