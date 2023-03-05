package ru.nsu.bolotov.exceptions;

public class UndefinedAliasInDefinitionMap extends RuntimeException {
    public UndefinedAliasInDefinitionMap() {
        super();
    }

    public UndefinedAliasInDefinitionMap(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Undefined alias in definition map";
    }
}
