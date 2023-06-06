package ru.nsu.bolotov.view;

import ru.nsu.bolotov.utils.UtilConsts;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatWindow implements Window {
    private final JFrame frame;
    private final JPanel panel;
    private final JTextArea chatArea;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ChatWindow() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                support.firePropertyChange(UtilConsts.EventTypesConsts.DISCONNECT, null, null);
                super.windowClosing(event);
            }
        });
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

        // TODO: add users area

        chatArea = new JTextArea(10, 30);
        chatArea.setLineWrap(true);
        chatArea.setEnabled(false);
        chatArea.setFont(GUIHelper.MESSAGE_STANDARD_FONT);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(600, 50, 500, 450);
        panel.add(scrollPane);

        JLabel textFieldLabel = new JLabel();
        textFieldLabel.setFont(GUIHelper.GENERAL_FONT);
        textFieldLabel.setText("Enter your message:");
        textFieldLabel.setBounds(750, 490, 220, 80);
        panel.add(textFieldLabel);

        JTextField textField = new JTextField(30);
        textField.setFont(GUIHelper.MESSAGE_STANDARD_FONT);
        textField.setBounds(600, 550, 500, 80);
        panel.add(textField);

        JButton sendButton = new JButton("Send message");
        sendButton.setFont(GUIHelper.BUTTON_STANDARD_FONT);
        sendButton.setBounds(950, 640, 150, 70);
        sendButton.addActionListener(event -> {
            String message = textField.getText();
            textField.setText(UtilConsts.StringConsts.EMPTY_STRING);
            support.firePropertyChange(UtilConsts.EventTypesConsts.MESSAGE, null, message);
        });
        panel.add(sendButton);

        frame.add(panel);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
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

    public void updateChat(String incomingMessage) {
        String previousMessages = chatArea.getText();
        if (UtilConsts.StringConsts.EMPTY_STRING.equals(previousMessages)) {
            chatArea.setText(incomingMessage);
        } else {
            chatArea.setText(previousMessages + '\n' + incomingMessage);
        }
    }
}
