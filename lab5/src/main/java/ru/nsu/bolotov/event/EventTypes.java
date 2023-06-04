package ru.nsu.bolotov.event;

import ru.nsu.bolotov.utils.UtilConsts;

public enum EventTypes {
    LOG_IN (UtilConsts.EventTypesConsts.LOG_IN),
    NEW_CONNECT (UtilConsts.EventTypesConsts.NEW_CONNECT),
    USERS_LIST (UtilConsts.EventTypesConsts.USERS_LIST),
    MESSAGE (UtilConsts.EventTypesConsts.MESSAGE),
    DISCONNECT (UtilConsts.EventTypesConsts.DISCONNECT),
    SERVER_OK_RESPONSE (UtilConsts.EventTypesConsts.SERVER_OK_RESPONSE),
    SERVER_BAD_RESPONSE (UtilConsts.EventTypesConsts.SERVER_BAD_RESPONSE);

    private final String type;

    EventTypes(String type) {
        this.type = type;
    }

    public String getType() { // TODO
        return type;
    }
}
