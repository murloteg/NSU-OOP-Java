package ru.nsu.bolotov.threadpool.actors;

import ru.nsu.bolotov.car.Car;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.CarStorage;

import java.util.concurrent.TimeUnit;

public class Dealer implements Actor, Runnable {
    private final CarStorage cars;
    private final int dealersDelayTimeMsec;

    public Dealer(CarStorage cars, int dealersDelayTimeMsec) {
        this.cars = cars;
        this.dealersDelayTimeMsec = dealersDelayTimeMsec;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            executeTask();
        }
    }

    private void executeTask() {
        synchronized (cars) {
            while (cars.isEmpty()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(dealersDelayTimeMsec);
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
