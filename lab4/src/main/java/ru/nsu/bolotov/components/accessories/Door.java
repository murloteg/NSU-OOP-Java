package ru.nsu.bolotov.components.accessories;

import ru.nsu.bolotov.components.ComponentAnnotation;
import ru.nsu.bolotov.util.UtilConsts;

@ComponentAnnotation
public class Door extends Accessories {
    public Door(String type) {
        super(type, UtilConsts.ComponentsConsts.REQUIRED_DOORS_NUMBER);
        setIDType();
    }
}
