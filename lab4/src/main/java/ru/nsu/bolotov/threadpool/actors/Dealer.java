package ru.nsu.bolotov.threadpool.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.car.Car;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.CarStorage;

import java.util.concurrent.TimeUnit;

@Actor
public class Dealer implements Runnable {
    private final CarStorage cars;
    private int dealersDelayTimeMsec;
    private static final Logger LOGGER = LoggerFactory.getLogger(Dealer.class);
    private final boolean loggingStatus;

    public Dealer(CarStorage cars, int dealersDelayTimeMsec, boolean loggingStatus) {
        this.cars = cars;
        this.dealersDelayTimeMsec = dealersDelayTimeMsec;
        this.loggingStatus = loggingStatus;
    }

    public void setDelayTime(int delayTime) {
        this.dealersDelayTimeMsec = delayTime;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            executeTask();
        }
    }

    private void executeTask() {
        try {
            TimeUnit.MILLISECONDS.sleep(dealersDelayTimeMsec);
            System.out.println("Dealer waiting " + dealersDelayTimeMsec);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new BusinessInterruptedException();
        }
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
            Car soldCar = cars.getCar();
            cars.notifyAll();
            LOGGER.info("Dealer sold the car:\n {} ", soldCar);
        }
    }
}
