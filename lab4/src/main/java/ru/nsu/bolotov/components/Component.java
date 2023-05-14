package ru.nsu.bolotov.components;

public abstract class Component {
    protected String id;
    protected String type;

    public String getID() {
        return id;
    }

    protected Component(String type) {
        this.type = type.toUpperCase();
    }

    public void setIDType() {
        id = String.format("%s ID: %d", type.toUpperCase(), ComponentIDSetter.getNextID());
    }

    public String getType() {
        return type;
    }
}
