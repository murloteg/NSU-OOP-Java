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
    private final JTextArea usersListArea;
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
        usersLabel.setForeground(GUIHelper.CHAT_TEXT_COLOR_FOREGROUND);
        usersLabel.setFont(GUIHelper.LABELS_STANDARD_FONT);
        usersLabel.setText("Active users:");
        usersLabel.setBounds(190, 0, 220, 60);
        panel.add(usersLabel);

        usersListArea = new JTextArea(10, 15);
        usersListArea.setEditable(false);
        usersListArea.setBackground(GUIHelper.CHAT_TEXT_COLOR_BACKGROUND);
        usersListArea.setForeground(GUIHelper.CHAT_TEXT_COLOR_FOREGROUND);
        usersListArea.setFont(GUIHelper.LABELS_STANDARD_FONT);
        JScrollPane usersListAreaScrollPane = new JScrollPane(usersListArea);
        usersListAreaScrollPane.setBounds(100, 50, 370, 350);
        panel.add(usersListAreaScrollPane);

        chatArea = new JTextArea(10, 30);
        chatArea.setLineWrap(true);
        chatArea.setEditable(false);
        chatArea.setBackground(GUIHelper.CHAT_TEXT_COLOR_BACKGROUND);
        chatArea.setForeground(GUIHelper.CHAT_TEXT_COLOR_FOREGROUND);
        chatArea.setFont(GUIHelper.MESSAGE_STANDARD_FONT);
        JScrollPane textAreaScrollPane = new JScrollPane(chatArea);
        textAreaScrollPane.setBounds(600, 50, 500, 450);
        panel.add(textAreaScrollPane);

        JLabel textFieldLabel = new JLabel();
        textFieldLabel.setForeground(GUIHelper.CHAT_TEXT_COLOR_FOREGROUND);
        textFieldLabel.setFont(GUIHelper.LABELS_STANDARD_FONT);
        textFieldLabel.setText("Enter your message:");
        textFieldLabel.setBounds(740, 490, 270, 80);
        panel.add(textFieldLabel);

        JTextField textField = new JTextField(30);
        textField.setBackground(GUIHelper.CHAT_TEXT_COLOR_BACKGROUND);
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

    public void updateUsersLists(String usersList) {
        usersListArea.setText(usersList);
    }

    public void updateCurrentUser(String username) {
        String usernameInfo = username + "'s client";
        frame.setTitle(usernameInfo);
    }
}
