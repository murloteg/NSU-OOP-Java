package ru.nsu.bolotov.threadpool.actors;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.ComponentFactory;
import ru.nsu.bolotov.storages.ComponentStorage;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class Supplier implements Actor, Runnable {
    private final ComponentStorage<Component> components;
    private final int suppliersDelayTimeMsec;
    private final String componentType;

    public Supplier(ComponentStorage<Component> components, int suppliersDelayTimeMsec, String componentType) {
        this.components = components;
        this.suppliersDelayTimeMsec = suppliersDelayTimeMsec;
        this.componentType = componentType;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            executeTask();
        }
    }

    private void executeTask() {
        synchronized (components) {
            while (components.getSize() == components.getLimit()) {
                try {
                    System.out.println(String.format("Storage of %s is full...", componentType)); // FIXME
                    components.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            Component component;
            try {
                TimeUnit.MILLISECONDS.sleep(suppliersDelayTimeMsec);
                component = ComponentFactory.createComponent(componentType);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException exception) {
                throw new FailedCreationException();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new BusinessInterruptedException();
            }
            components.addComponent(component);
            components.notifyAll();
        }
    }
}
