package ru.nsu.bolotov.storages;

import ru.nsu.bolotov.car.Car;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;

public class CarStorage {
    private final LinkedList<Car> cars;
    private final int limit;
    private final PropertyChangeSupport support;

    public CarStorage(int limit) {
        this.limit = limit;
        cars = new LinkedList<>();
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void addCar(Car car) {
        cars.addLast(car);
    }

    public Car getCar() {
        support.firePropertyChange("isSleepingProcess", true, false);
        return cars.removeFirst();
    }

    public int getSize() {
        return cars.size();
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    public int getLimit() {
        return limit;
    }
}
