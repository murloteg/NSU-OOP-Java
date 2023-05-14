package ru.nsu.bolotov.storages;

import ru.nsu.bolotov.components.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

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

    public int getNumberOfSpecifiedComponents(String type) {
        int counter = 0;
        for (Component component : components) {
            if (type.equals(component.getType())) {
                ++counter;
            }
        }
        return counter;
    }

    public Optional<Component> getSpecifiedComponent(String type) {
        int index = 0;
        for (Component component : components) {
            if (component.getType().equals(type)) {
                return Optional.of(components.remove(index));
            }
            ++index;
        }
        return Optional.empty();
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
