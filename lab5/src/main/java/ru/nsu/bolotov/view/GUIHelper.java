package ru.nsu.bolotov.view;

import ru.nsu.bolotov.utils.UtilConsts;

import java.awt.*;

public class GUIHelper {
    public static final Color GENERAL_COLOR = new Color(239, 226, 186);
    public static final Color CHAT_TEXT_COLOR_FOREGROUND = new Color(5, 56, 107);
    public static final Color CHAT_TEXT_COLOR_BACKGROUND = new Color(250, 250, 250);
    public static final Font GENERAL_FONT = new Font("STIX Two Math", Font.PLAIN, 24);
    public static final Font BUTTON_STANDARD_FONT = new Font("PT Sans", Font.PLAIN, 18);
    public static final Font MESSAGE_STANDARD_FONT = new Font("PT Sans", Font.PLAIN, 20);
    public static final Font LABELS_STANDARD_FONT = new Font("PT Sans", Font.BOLD, 28);

    private GUIHelper() {
        throw new IllegalStateException(UtilConsts.StringConsts.INSTANTIATION_MESSAGE);
    }
}
