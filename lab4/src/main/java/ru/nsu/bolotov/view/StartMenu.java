package ru.nsu.bolotov.view;

import ru.nsu.bolotov.util.UtilConsts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StartMenu implements PartOfView {
    private final JFrame menuFrame;
    private final PropertyChangeSupport support;
    
    public StartMenu() {
        support = new PropertyChangeSupport(this);
        menuFrame = new JFrame();
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuFrame.setTitle("Car Factory");
        menuFrame.setBounds(0, 0, 1200, 800);

        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setLayout(null);

        JButton exit = new JButton("Exit");
        exit.setBounds(100, 100, UtilConsts.GUIConsts.DEFAULT_BUTTON_WIDTH, UtilConsts.GUIConsts.DEFAULT_BUTTON_HEIGHT);
        exit.addActionListener(event -> {
            menuFrame.dispatchEvent(new WindowEvent(menuFrame, WindowEvent.WINDOW_CLOSING));
        });
        panel.add(exit);

        JButton start = new JButton("Start");
        start.setBounds(600, 100, UtilConsts.GUIConsts.DEFAULT_BUTTON_WIDTH, UtilConsts.GUIConsts.DEFAULT_BUTTON_HEIGHT);
        start.addActionListener(event -> {
            support.firePropertyChange(UtilConsts.GUIConsts.ACTIVE_FRAME, UtilConsts.GUIConsts.START_MENU, UtilConsts.GUIConsts.APPLICATION_VIEW);
            stopDisplayFrame();
        });
        panel.add(start);

        menuFrame.add(panel);
        menuFrame.setVisible(true);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void startDisplayFrame() {
        menuFrame.setVisible(true);
    }

    @Override
    public void stopDisplayFrame() {
        menuFrame.setVisible(false);
    }
}
