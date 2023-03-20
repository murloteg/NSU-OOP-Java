package ru.nsu.bolotov.gui;

import ru.nsu.bolotov.util.UtilConsts;

import javax.swing.*;
import java.awt.*;

public final class GraphicView {
    private static final int WIDTH_RESOLUTION = 1200;
    private static final int HEIGHT_RESOLUTION = 720;

    public static void showField() {
        JFrame frame = getFrame();
        frame.setVisible(true);
    }

    private static JFrame getFrame() {
        JFrame frame = new JFrame();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds((dimension.width - WIDTH_RESOLUTION) / 2, (dimension.height - HEIGHT_RESOLUTION) / 2, WIDTH_RESOLUTION, HEIGHT_RESOLUTION);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Minesweeper");
        return frame;
    }

    private GraphicView() {
        throw new IllegalStateException(UtilConsts.StringConsts.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
