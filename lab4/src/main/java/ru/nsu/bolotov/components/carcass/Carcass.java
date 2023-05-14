package ru.nsu.bolotov.components.carcass;

import ru.nsu.bolotov.components.Component;
import ru.nsu.bolotov.components.ComponentAnnotation;

@ComponentAnnotation
public class Carcass extends Component {
    public Carcass(String type) {
        super(type);
        setIDType();
    }
}
