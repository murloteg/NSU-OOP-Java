package ru.nsu.bolotov.view;

import ru.nsu.bolotov.utils.UtilConsts;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ClientMenu implements Window {
    private final JFrame frame;
    private final JPanel panel;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    public ClientMenu() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Menu");
        frame.setBounds(0, 0, 1200, 750);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(GUIHelper.GENERAL_COLOR);

        JTextField textField = new JTextField("username", 12);
        textField.setFont(GUIHelper.GENERAL_FONT);
        textField.setBounds(500, 210, 200, 50);
        panel.add(textField);

        JLabel textFieldLabel = new JLabel();
        textFieldLabel.setFont(GUIHelper.GENERAL_FONT);
        textFieldLabel.setBounds(555, 170, 200, 50);
        textFieldLabel.setText("Login as:");
        panel.add(textFieldLabel);

        JButton startButton = new JButton("Log in");
        startButton.setFont(GUIHelper.BUTTON_STANDARD_FONT);
        startButton.setBounds(575, 270, 120, 50);
        startButton.addActionListener(event -> {
            support.firePropertyChange(UtilConsts.StringConsts.LOG_IN_BUTTON_HAS_BEEN_PRESSED, null, textField.getText());
        });
        panel.add(startButton);

        frame.add(panel);
        startFrameDisplay();
    }

    public void addPropertyListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void startFrameDisplay() {
        frame.setVisible(true);
    }

    @Override
    public void stopFrameDisplay() {
        frame.setVisible(false);
    }
}
