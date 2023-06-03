package ru.nsu.bolotov.event;

import java.io.Serializable;

public class Event implements Serializable {
    private final String eventType;

    public Event(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
