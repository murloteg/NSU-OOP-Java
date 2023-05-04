package ru.nsu.bolotov.storages;

import ru.nsu.bolotov.car.Car;

import java.util.LinkedList;


public class CarStorage {
    private final LinkedList<Car> cars;
    private final int limit;

    public CarStorage(int limit) {
        this.limit = limit;
        cars = new LinkedList<>();
    }

    public void addCar(Car car) {
        cars.addLast(car);
    }

    public Car getCar() {
        return cars.removeFirst();
    }

    public int getSize() {
        return cars.size();
    }

    public int getLimit() {
        return limit;
    }
}
