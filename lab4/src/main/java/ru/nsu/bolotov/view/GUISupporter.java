package ru.nsu.bolotov.view;

import ru.nsu.bolotov.util.UtilConsts;

import java.awt.*;

public final class GUISupporter {
    public static final Font LABEL_FONT = new Font("STIX Two Math", Font.PLAIN, 26);
    public static final Color STANDART_COLOR = new Color(203, 177, 239);

    private GUISupporter() {
        throw new IllegalStateException(UtilConsts.StringConsts.INSTANTIATION_MESSAGE);
    }
}
