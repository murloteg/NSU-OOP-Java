package ru.nsu.bolotov.threadpool.tasks;

import ru.nsu.bolotov.car.Car;
import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.components.accessories.Accessories;
import ru.nsu.bolotov.components.accessories.Door;
import ru.nsu.bolotov.components.accessories.Wheel;
import ru.nsu.bolotov.components.carcass.Carcass;
import ru.nsu.bolotov.components.engine.Engine;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.util.UtilConsts;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BuildTask implements Task {
    private final ComponentStorage<Component> carcasses;
    private final ComponentStorage<Component> engines;
    private final ComponentStorage<Component> accessories;
    private final CarStorage cars;
    private Carcass carcass;
    private Engine engine;
    private final List<Accessories> accessoriesList  = new LinkedList<>();

    public BuildTask(ComponentStorage<Component> carcasses, ComponentStorage<Component> engines,
                     ComponentStorage<Component> accessories, CarStorage cars) {
        this.carcasses = carcasses;
        this.engines = engines;
        this.accessories = accessories;
        this.cars = cars;
    }

    @Override
    public void doWork() {
        prepareAllComponents();
        synchronized (cars) {
            while (cars.getSize() == cars.getLimit()) {
                try {
                    System.out.println("Car storage is full..."); // FIXME
                    cars.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            Car createdCar = new Car(carcass, engine, accessoriesList);
            cars.addCar(createdCar);
            System.out.println("Car was created: " + createdCar); // FIXME
            cars.notifyAll();
        }
    }

    private void prepareAllComponents() {
        synchronized (carcasses) {
            while (carcasses.isEmpty()) {
                try {
                    System.out.println("Worker waiting carcasses..."); // FIXME
                    carcasses.notifyAll();
                    carcasses.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            carcass = (Carcass) carcasses.getNextComponent();
            carcasses.notifyAll();
        }
        synchronized (engines) {
            while (engines.isEmpty()) {
                try {
                    System.out.println("Worker waiting engines..."); // FIXME
                    engines.notifyAll();
                    engines.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            engine = (Engine) engines.getNextComponent();
            engines.notifyAll();
        }
        synchronized (accessories) {
            while (accessories.getNumberOfSpecifiedComponents("WHEEL") < UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER ||
                    accessories.getNumberOfSpecifiedComponents("DOOR") < UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER) {
                try {
                    System.out.println("Worker waiting accessories..."); // FIXME
                    accessories.notifyAll();
                    accessories.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            prepareAccessories();
            accessories.notifyAll();
        }
    }

    private void prepareAccessories() {
        while (getNumberOfSpecifiedAccessories("WHEEL") < UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER ||
                getNumberOfSpecifiedAccessories("DOOR") < UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER) {
            if (getNumberOfSpecifiedAccessories("WHEEL") != UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER) {
                Optional<Component> component = accessories.getSpecifiedComponent("WHEEL");
                component.ifPresent(value -> accessoriesList.add((Wheel) value));
            }
            if (getNumberOfSpecifiedAccessories("DOOR") != UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER) {
                Optional<Component> component = accessories.getSpecifiedComponent("DOOR");
                component.ifPresent(value -> accessoriesList.add((Door) value));
            }
        }
    }

    private int getNumberOfSpecifiedAccessories(String type) {
        int counter = 0;
        for (Accessories accessory : accessoriesList) {
            if (type.equals(accessory.getType())) {
                ++counter;
            }
        }
        return counter;
    }
}
