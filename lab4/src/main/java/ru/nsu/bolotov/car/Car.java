package ru.nsu.bolotov.car;

import ru.nsu.bolotov.components.accessories.Accessories;
import ru.nsu.bolotov.components.carcass.Carcass;
import ru.nsu.bolotov.components.engine.Engine;

import java.util.List;

public class Car {
    private final Engine engine;
    private final Carcass carcass;
    private final List<? extends Accessories> accessories;

    public Car(Carcass carcass, Engine engine, List<? extends Accessories> accessories) {
        this.engine = engine;
        this.carcass = carcass;
        this.accessories = accessories;
    }
}
