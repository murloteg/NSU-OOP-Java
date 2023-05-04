package ru.nsu.bolotov.util;

public final class UtilConsts {
    public static final class ComponentsConsts {
        public static final int REQUIRED_WHEELS_NUMBER = 4;

        private ComponentsConsts() {
            throw new IllegalStateException("Instantiation of util class");
        }
    }

    private UtilConsts() {
        throw new IllegalStateException("Instantiation of util class");
    }
}
