package ru.nsu.bolotov.utils;

public final class UtilConsts {
    public static final class ConnectionConsts {
        public static final String IP_ADDR = "localhost";
        public static final int WAITING_TIME_SEC = 15;

        private ConnectionConsts() {
            throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
        }
    }

    public static final class StringConsts {
        public static final String SERVER = "SERVER";
        public static final String CLIENT = "CLIENT";
        public static final String INACTIVE_SERVER = "INACTIVE_SERVER";
        public static final String INSTANTIATION_MESSAGE = "Instantiation of util class";

        private StringConsts() {
            throw new IllegalStateException(INSTANTIATION_MESSAGE);
        }
    }

    private UtilConsts() {
        throw new IllegalStateException(StringConsts.INSTANTIATION_MESSAGE);
    }
}
