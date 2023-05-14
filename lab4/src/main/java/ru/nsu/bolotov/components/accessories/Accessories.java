package ru.nsu.bolotov.components.accessories;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.components.ComponentAnnotation;

@ComponentAnnotation
public abstract class Accessories extends Component {
    protected final int requiredComponentNumber;

    protected Accessories(String componentType, int requiredComponentNumber) {
        super(componentType);
        this.requiredComponentNumber = requiredComponentNumber;
    }
}
