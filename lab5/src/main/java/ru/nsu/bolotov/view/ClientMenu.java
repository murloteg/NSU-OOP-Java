package ru.nsu.bolotov.view;

import javax.swing.*;

public class ClientMenu {
    private final JFrame frame;
    private final JPanel panel;
    
    public ClientMenu() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Chat menu");
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

        frame.add(panel);
        frame.setVisible(true);
    }
}
