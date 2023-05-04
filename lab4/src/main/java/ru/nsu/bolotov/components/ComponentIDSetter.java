package ru.nsu.bolotov.components;

public final class ComponentIDSetter {
    private static long id = 0;

    public static long getNextID() {
        long previousId = id;
        ++id;
        return previousId;
    }

    private ComponentIDSetter() {
        throw new IllegalStateException("Instantiation of class without state");
    }
}
