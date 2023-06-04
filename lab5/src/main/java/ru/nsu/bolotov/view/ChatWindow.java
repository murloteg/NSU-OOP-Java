package ru.nsu.bolotov.view;

import javax.swing.*;

public class ChatWindow implements Window {
    private final JFrame frame;
    private final JPanel panel;

    public ChatWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Chat window");
        frame.setBounds(0, 0, 1200, 750);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(GUIHelper.GENERAL_COLOR);

        JLabel usersLabel = new JLabel();
        usersLabel.setFont(GUIHelper.GENERAL_FONT);
        usersLabel.setText("Users:");
        usersLabel.setBounds(100, 80, 220, 80);
        panel.add(usersLabel);

        JLabel textAreaLabel = new JLabel();
        textAreaLabel.setFont(GUIHelper.GENERAL_FONT);
        textAreaLabel.setText("Enter your message:");
        textAreaLabel.setBounds(610, 80, 220, 80);
        panel.add(textAreaLabel);

        JTextField textField = new JTextField(40);
        textField.setFont(GUIHelper.MESSAGE_STANDARD_FONT);
        textField.setBounds(600, 150, 220, 80);
        panel.add(textField);

        frame.add(panel);
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
