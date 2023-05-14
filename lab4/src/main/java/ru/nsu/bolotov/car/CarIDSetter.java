package ru.nsu.bolotov.car;

public final class CarIDSetter {
    private static int id = 0;

    public static int getNextID() {
        int previousId = id;
        ++id;
        return previousId;
    }

    public static int getNumberOfCreatedCars() {
        return id;
    }

    private CarIDSetter() {
        throw new IllegalStateException("Instantiation of class without state");
    }
}
