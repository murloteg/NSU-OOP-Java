package ru.nsu.bolotov.components;

public abstract class Component {
    protected String id;

    public String getID() {
        return id;
    }

    public void setID(String type) {
        id = String.format("%s ID: %d", type, IDSetter.getNextComponentID());
    }
}
