package ru.nsu.bolotov.threadpool.tasks;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.exceptions.BusinessInterruptedException;
import ru.nsu.bolotov.storages.ComponentStorage;

public class ReplenishmentTask implements Task {
    private final ComponentStorage<Component> components;
    private final int delayTime;
    private final String componentType;

    public ReplenishmentTask(ComponentStorage<Component> components, int delayTime, String componentType) {
        this.components = components;
        this.delayTime = delayTime;
        this.componentType = componentType;
    }

    @Override
    public void doWork() {
        synchronized(components) {
            while (components.getSize() == components.getLimit()) {
                try {
                    components.wait();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new BusinessInterruptedException();
                }
            }
            // ...
        }
    }
}