package ru.nsu.bolotov.view;

import ru.nsu.bolotov.utils.UtilConsts;

import java.awt.*;

public class GUIHelper {
    public static final Color GENERAL_COLOR = new Color(229, 222, 161);
    public static final Font GENERAL_FONT = new Font("STIX Two Math", Font.PLAIN, 24);

    private GUIHelper() {
        throw new IllegalStateException(UtilConsts.StringConsts.INSTANTIATION_MESSAGE);
    }
}
