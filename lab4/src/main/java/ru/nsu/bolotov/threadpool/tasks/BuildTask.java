package ru.nsu.bolotov.threadpool.tasks;

import ru.nsu.bolotov.car.Car;
import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.components.accessories.Wheel;
import ru.nsu.bolotov.components.carcass.Carcass;
import ru.nsu.bolotov.components.engine.Engine;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.CarStorage;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.util.UtilConsts;

import java.util.LinkedList;
import java.util.List;

public class BuildTask implements Task {
    private final ComponentStorage<Component> carcasses;
    private final ComponentStorage<Component> engines;
    private final ComponentStorage<Component> accessories;
    private final CarStorage cars;
    private Carcass carcass;
    private Engine engine;
    private final List<Wheel> wheels = new LinkedList<>();

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
                    cars.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            cars.addCar(new Car(carcass, engine, wheels));
            System.out.println(cars.getCar().toString()); // TODO: remove later.
        }
    }

    private void prepareAllComponents() {
        synchronized (carcasses) {
            while (carcasses.isEmpty()) {
                try {
                    carcasses.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            carcass = (Carcass) carcasses.getNextComponent();
        }
        synchronized (engines) {
            while (engines.isEmpty()) {
                try {
                    engines.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            engine = (Engine) engines.getNextComponent();
        }
        synchronized (accessories) {
            while (accessories.getSize() != UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER) {
                try {
                    accessories.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            for (int i = 0; i < UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER; ++i) {
                wheels.add((Wheel) accessories.getNextComponent());
            }
        }
    }
}
