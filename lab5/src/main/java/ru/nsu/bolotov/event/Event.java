package ru.nsu.bolotov.event;

import java.io.Serializable;

public class Event implements Serializable {
    private final EventTypes eventType;
    private final String username;
    private final String description;

    public Event(EventTypes eventType, String username, String description) {
        this.eventType = eventType;
        this.username = username;
        this.description = description;
    }

    public EventTypes getEventType() {
        return eventType; // TODO
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }
}
