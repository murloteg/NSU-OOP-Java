package ru.nsu.bolotov.storages;

import ru.nsu.bolotov.components.Component;

import java.util.Iterator;
import java.util.LinkedList;

public class ComponentStorage <T extends Component> implements Iterable<T> {
    private final LinkedList<T> components;
    private final int requiredComponentsNumber;
    private final int limit;

    public ComponentStorage(int requiredComponentsNumber, int limit) {
        this.requiredComponentsNumber = requiredComponentsNumber;
        this.limit = limit;
        components = new LinkedList<>();
    }

    public void addComponent(T component) {
        components.addLast(component);
    }

    public Component getNextComponent() {
        return components.removeFirst();
    }

    public int getRequiredComponentsNumber() {
        return requiredComponentsNumber;
    }

    public int getLimit() {
        return limit;
    }

    public int getSize() {
        return components.size();
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return components.iterator();
    }
}
