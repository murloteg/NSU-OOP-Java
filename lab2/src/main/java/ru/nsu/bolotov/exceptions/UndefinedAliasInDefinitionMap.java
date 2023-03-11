package ru.nsu.bolotov.exceptions;

public class UndefinedAliasInDefinitionMap extends RuntimeException {
    public UndefinedAliasInDefinitionMap() {
        super();
    }

    @Override
    public String getMessage() {
        return "Undefined alias in definition map";
    }
}
