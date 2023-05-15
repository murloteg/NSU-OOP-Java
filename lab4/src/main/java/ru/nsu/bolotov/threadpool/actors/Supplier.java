package ru.nsu.bolotov.threadpool.actors;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.exceptions.FailedCreationException;
import ru.nsu.bolotov.factory.ComponentFactory;
import ru.nsu.bolotov.storages.ComponentStorage;
import ru.nsu.bolotov.threadpool.tasks.SupplierOrder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Actor
public class Supplier implements Runnable {
    private final ComponentStorage<Component> components;
    private int suppliersDelayTimeMsec;
    private final SupplierOrder supplierOrder;
    private final List<String> componentScheduler;
    private int schedulerIndex;

    public Supplier(ComponentStorage<Component> components, int suppliersDelayTimeMsec, SupplierOrder supplierOrder) {
        this.components = components;
        this.suppliersDelayTimeMsec = suppliersDelayTimeMsec;
        this.supplierOrder = supplierOrder;
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
            return;
        }
        synchronized (components) {
            while (components.getSize() == components.getLimit()) {
                try {
                    components.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    return;
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
        String[] componentTypes = supplierOrder.getComponentTypes();
        int[] requiredNumbersOfComponents = supplierOrder.getRequiredNumbersOfComponents();
        int minFrequency = findMinFrequencyOfComponents(requiredNumbersOfComponents);
        for (int i = 0; i < componentTypes.length; ++i) {
            for (int j = 0; j < requiredNumbersOfComponents[i] / minFrequency; ++j) {
                componentScheduler.add(componentTypes[i]);
            }
        }
    }

    private int findMinFrequencyOfComponents(int[] requiredNumbersOfComponents) {
        int minFrequency = Integer.MAX_VALUE;
        for (Integer frequency : requiredNumbersOfComponents) {
            minFrequency = Math.min(minFrequency, frequency);
        }
        return minFrequency;
    }

    private void updateSchedulerIndex() {
        ++schedulerIndex;
        schedulerIndex %= componentScheduler.size();
    }
}
