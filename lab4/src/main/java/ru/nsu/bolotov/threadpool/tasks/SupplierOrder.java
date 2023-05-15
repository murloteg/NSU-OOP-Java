package ru.nsu.bolotov.threadpool.tasks;

public class SupplierOrder {
    private final String[] componentTypes;
    private final int[] requiredNumbersOfComponents;

    public SupplierOrder(String[] componentTypes, int[] requiredNumbersOfComponents) {
        this.componentTypes = componentTypes;
        this.requiredNumbersOfComponents = requiredNumbersOfComponents;
        validateInputParameters();
    }

    public String[] getComponentTypes() {
        return componentTypes;
    }

    public int[] getRequiredNumbersOfComponents() {
        return requiredNumbersOfComponents;
    }

    private void validateInputParameters() {
        if (componentTypes.length == 0 || componentTypes.length != requiredNumbersOfComponents.length) {
            throw new IllegalArgumentException("Incorrect number of components/required numbers");
        }
    }
}
