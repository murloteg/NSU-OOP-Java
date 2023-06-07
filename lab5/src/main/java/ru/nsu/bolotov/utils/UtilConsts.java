package ru.nsu.bolotov.utils;

public final class UtilConsts {
    public static final class ConnectionConsts {
        public static final String IP_ADDR = "localhost";
        public static final String SPECIAL_INFO_USERNAME = StringConsts.EMPTY_STRING;
        public static final int MESSAGE_CACHE_SIZE = 5;

        private ConnectionConsts() {
            throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
        }
    }

    public static final class EventTypesConsts {
        public static final String LOG_IN = "LOG_IN";
        public static final String NEW_CONNECT = "NEW_CONNECT";
        public static final String USERS_LIST = "USERS_LIST";
        public static final String MESSAGE = "MESSAGE";
        public static final String DISCONNECT = "DISCONNECT";
        public static final String SERVER_OK_RESPONSE = "SERVER_OK_RESPONSE";
        public static final String SERVER_BAD_RESPONSE = "SERVER_BAD_RESPONSE";

        private EventTypesConsts() {
            throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
        }
    }

    public static final class StringConsts {
        public static final String EMPTY_STRING = "";
        public static final String LOG_IN_BUTTON_HAS_BEEN_PRESSED = "LOG_IN_BUTTON_HAS_BEEN_PRESSED";
        public static final String INSTANTIATION_MESSAGE = "Instantiation of util class";

        private StringConsts() {
            throw new IllegalStateException(INSTANTIATION_MESSAGE);
        }
    }

    private UtilConsts() {
        throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
    }
}
