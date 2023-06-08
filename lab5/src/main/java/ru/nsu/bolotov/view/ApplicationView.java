package ru.nsu.bolotov.view;

import ru.nsu.bolotov.utils.UtilConsts;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ApplicationView implements PropertyChangeListener {
    private final ClientMenu chatMenu;
    private final ChatWindow chatWindow;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ApplicationView() {
        chatMenu = new ClientMenu();
        chatMenu.addPropertyListener(this);
        chatWindow = new ChatWindow();
        chatWindow.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case UtilConsts.StringConsts.LOG_IN_BUTTON_HAS_BEEN_PRESSED: {
                support.firePropertyChange(UtilConsts.EventTypesConsts.LOG_IN, null, event.getNewValue());
                break;
            }
            case UtilConsts.EventTypesConsts.MESSAGE: {
                support.firePropertyChange(UtilConsts.EventTypesConsts.MESSAGE, null, event.getNewValue());
                break;
            }
            case UtilConsts.EventTypesConsts.DISCONNECT: {
                support.firePropertyChange(UtilConsts.EventTypesConsts.DISCONNECT, null, null);
                break;
            }
            default: {
                throw new IllegalArgumentException(UtilConsts.StringConsts.UNEXPECTED_EVENT_TYPE);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void displayChat() {
        chatMenu.stopFrameDisplay();
        chatWindow.startFrameDisplay();
    }

    public void displayEventMessage(String eventMessage) {
        chatWindow.updateChat(eventMessage);
    }

    public void displayUsersList(String usersList) {
        chatWindow.updateUsersLists(usersList);
    }

    public void displayCurrentUser(String username) {
        chatWindow.updateCurrentUser(username);
    }

    public void displayError(String error) {
        JOptionPane.showMessageDialog(null, error, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}
