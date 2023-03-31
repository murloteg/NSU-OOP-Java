package ru.nsu.bolotov.util;

public final class UtilConsts {
    public static final class FieldConsts {
        public static final int DEFAULT_FIELD_SIZE = 9;
        public static final int DEFAULT_NUMBER_OF_MINES = 10;

        private FieldConsts() {
            throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
        }
    }

    public static final class StatusesOfCells {
        public static final int EMPTY = 0;
        public static final int ONE_NEIGHBOR = 1;
        public static final int TWO_NEIGHBORS = 2;
        public static final int THREE_NEIGHBORS = 3;
        public static final int FOUR_NEIGHBORS = 4;
        public static final int FIVE_NEIGHBORS = 5;
        public static final int SIX_NEIGHBORS = 6;
        public static final int SEVEN_NEIGHBORS = 7;
        public static final int EIGHT_NEIGHBORS = 8;
        public static final int BOMB = 9;
        public static final int FLAGGED = 10;
        public static final int CLEARED = 11;
        public static final int VISITED_EMPTY = 12;
        public static final int HIDDEN = 13;
        public static final int OPEN = 14;


        private StatusesOfCells() {
            throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
        }
    }

    public static final class RandomConsts {
        public static final int REMAINDER = 15;

        private RandomConsts() {
            throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
        }
    }

    public static final class ViewTypes {
        public static final int TEXT = 1;
        public static final int GRAPHIC_INTERFACE = 2;

        private ViewTypes() {
            throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
        }
    }

    public static final class TextViewConsts {
        public static final int MAX_HEX_FIELD_SIZE = 16;

        private TextViewConsts() {
            throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
        }
    }

    public static final class StringConsts {
        public static final String UTILITY_CLASS_INSTANTIATION_MESSAGE = "Instantiation of utility class";

        private StringConsts() {
            throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
        }
    }

    private UtilConsts() {
        throw new IllegalStateException(StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
