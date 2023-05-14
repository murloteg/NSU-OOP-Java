package ru.nsu.bolotov.util;

import static ru.nsu.bolotov.util.UtilConsts.StringConsts.INSTANTIATION_MESSAGE;

public final class UtilConsts {
    public static final class ComponentsConsts {
        public static final int REQUIRED_WHEELS_NUMBER = 4;
        public static final int REQUIRED_DOORS_NUMBER = 2;
        public static final int REQUIRED_ENGINES_NUMBER = 1;
        public static final int REQUIRED_CARCASSES_NUMBER = 1;

        private ComponentsConsts() {
            throw new IllegalStateException(INSTANTIATION_MESSAGE);
        }
    }

    public static final class TimeConsts {
        public static final int STANDARD_DEALER_DELAY_TIME_MSEC = 2000;
        public static final int STANDARD_SUPPLIER_DELAY_TIME_MSEC = 1500;
        public static final int MIN_SUPPLIER_DELAY_TIME_MSEC = 0;
        public static final int MAX_SUPPLIER_DELAY_TIME_MSEC = 5000;
        public static final int MIN_DEALER_DELAY_TIME_MSEC = 0;
        public static final int MAX_DEALER_DELAY_TIME_MSEC = 10000;

        private TimeConsts() {
            throw new IllegalStateException(INSTANTIATION_MESSAGE);
        }
    }

    public static final class StringConsts {
        public static final String INSTANTIATION_MESSAGE = "Instantiation of util class";

        private StringConsts() {
            throw new IllegalStateException(INSTANTIATION_MESSAGE);
        }
    }

    public static final class GUIConsts {
        public static final int DEFAULT_BUTTON_WIDTH = 160;
        public static final int DEFAULT_BUTTON_HEIGHT = 80;
        public static final String START_MENU = "startMenu";
        public static final String APPLICATION_VIEW = "applicationView";
        public static final String ACTIVE_FRAME = "activeFrame";

        private GUIConsts() {
            throw new IllegalStateException(INSTANTIATION_MESSAGE);
        }
    }

    private UtilConsts() {
        throw new IllegalStateException(INSTANTIATION_MESSAGE);
    }
}
