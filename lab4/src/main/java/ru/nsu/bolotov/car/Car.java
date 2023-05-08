package ru.nsu.bolotov.car;

import ru.nsu.bolotov.components.accessories.Accessories;
import ru.nsu.bolotov.components.carcass.Carcass;
import ru.nsu.bolotov.components.engine.Engine;

import java.util.List;

public class Car {
    private final Engine engine;
    private final Carcass carcass;
    private final List<? extends Accessories> accessories;
    private final long id;

    public Car(Carcass carcass, Engine engine, List<? extends Accessories> accessories) {
        this.engine = engine;
        this.carcass = carcass;
        this.accessories = accessories;
        id = CarIDSetter.getNextID();
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder(String.format("[Car ID: %d]%n", id));
        info.append("---").append(carcass.getID()).append("\n");
        info.append("---").append(engine.getID()).append("\n");
        for (Accessories accessory : accessories) {
            info.append("---").append(accessory.getID()).append("\n");
        }
        return info.toString();
    }
}
