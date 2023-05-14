package ru.nsu.bolotov.threadpool.actors;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.ComponentFactory;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.util.UtilConsts;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Actor
public class Supplier implements Runnable {
    private final ComponentStorage<Component> components;
    private int suppliersDelayTimeMsec;
    private final String[] componentTypes;
    private final List<String> componentScheduler;
    private int schedulerIndex;

    public Supplier(ComponentStorage<Component> components, int suppliersDelayTimeMsec, String[] componentTypes) {
        this.components = components;
        this.suppliersDelayTimeMsec = suppliersDelayTimeMsec;
        this.componentTypes = componentTypes;
        for (String type : this.componentTypes) {
            type = type.toUpperCase();
        }
        componentScheduler = new ArrayList<>();
        schedulerIndex = 0;
        prepareComponentScheduler();
    }

    public void setDelayTime(int delayTime) {
        this.suppliersDelayTimeMsec = delayTime;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            executeTask();
        }
    }

    private void executeTask() {
        try {
            TimeUnit.MILLISECONDS.sleep(suppliersDelayTimeMsec);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new BusinessInterruptedException();
        }
        synchronized (components) {
            while (components.getSize() == components.getLimit()) {
                try {
                    components.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            Component component;
            try {
                component = ComponentFactory.createComponent(componentScheduler.get(schedulerIndex) );
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException exception) {
                throw new FailedCreationException();
            }
            updateSchedulerIndex();
            components.addComponent(component);
            components.notifyAll();
        }
    }

    private void prepareComponentScheduler() {
        if (componentTypes.length == 1) {
            componentScheduler.add(componentTypes[0]);
        } else {
            int minRequiredNumber = Math.min(UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER, UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER);
            for (int i = 0; i < UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER / minRequiredNumber; ++i) {
                componentScheduler.add(componentTypes[0]);
            }
            for (int i = 0; i < UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER / minRequiredNumber; ++i) {
                componentScheduler.add(componentTypes[1]);
            }
        }
    }

    private void updateSchedulerIndex() {
        ++schedulerIndex;
        schedulerIndex %= componentScheduler.size();
    }
}
