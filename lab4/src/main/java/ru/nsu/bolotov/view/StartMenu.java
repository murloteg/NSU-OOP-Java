package ru.nsu.bolotov.view;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class StartMenu {
    private final JFrame menuFrame;
    
    public StartMenu() {
        menuFrame = new JFrame();
        JPanel panel = new JPanel();
        
        JButton start = new JButton("Start");
        start.addActionListener(event -> {
            menuFrame.setEnabled(false);
        });
        panel.add(start);

        JButton exit = new JButton("Exit");
        exit.addActionListener(event -> {
            menuFrame.dispatchEvent(new WindowEvent(menuFrame, WindowEvent.WINDOW_CLOSED));
        });
        panel.add(exit);
    }
}
