package ru.nsu.bolotov.components;

public final class IDSetter {
    private static long id = 0;

    public static long getNextComponentID() {
        long previousId = id;
        ++id;
        return previousId;
    }

    private IDSetter() {
        throw new IllegalStateException("Instantiation of class without state");
    }
}
