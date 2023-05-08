package ru.nsu.bolotov.threadpool.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.car.Car;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.CarStorage;

import java.util.concurrent.TimeUnit;

public class Dealer implements Actor, Runnable {
    private final CarStorage cars;
    private final int dealersDelayTimeMsec;
    private static final Logger LOGGER = LoggerFactory.getLogger(Dealer.class);
    private final boolean loggingStatus;

    public Dealer(CarStorage cars, int dealersDelayTimeMsec, boolean loggingStatus) {
        this.cars = cars;
        this.dealersDelayTimeMsec = dealersDelayTimeMsec;
        this.loggingStatus = loggingStatus;
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
                    if (loggingStatus) {
                        LOGGER.info("Dealer waiting car...");
                    }
                    cars.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(dealersDelayTimeMsec);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new BusinessInterruptedException();
            }
            Car soldCar = cars.getCar();
            LOGGER.info("Dealer sold the car:\n {} ", soldCar);
        }
    }
}
