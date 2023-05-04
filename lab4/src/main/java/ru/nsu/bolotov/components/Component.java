package ru.nsu.bolotov.components;

public abstract class Component {
    protected String id;

    public String getID() {
        return id;
    }

    public void setIDType(String type) {
        id = String.format("%s ID: %d", type, ComponentIDSetter.getNextID());
    }
}
