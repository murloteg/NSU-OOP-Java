package ru.nsu.bolotov.car;

public final class CarIDSetter {
    private static long id = 0;

    public static long getNextID() {
        long previousId = id;
        ++id;
        return previousId;
    }

    private CarIDSetter() {
        throw new IllegalStateException("Instantiation of class without state");
    }
}
