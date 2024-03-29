package ru.nsu.bolotov.components.accessories;

import ru.nsu.bolotov.components.ComponentAnnotation;
import ru.nsu.bolotov.util.UtilConsts;

@ComponentAnnotation
public class Wheel extends Accessories {
    public Wheel(String type) {
        super(type, UtilConsts.ComponentsConsts.REQUIRED_WHEELS_NUMBER);
        setIDType();
    }
}
