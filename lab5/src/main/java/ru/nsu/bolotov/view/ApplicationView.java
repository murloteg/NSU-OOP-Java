package ru.nsu.bolotov.view;

import ru.nsu.bolotov.utils.UtilConsts;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ApplicationView implements PropertyChangeListener {
    private final ClientMenu menu;
    private final ChatWindow chatWindow;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ApplicationView() {
        menu = new ClientMenu();
        menu.addPropertyListener(this);
        chatWindow = new ChatWindow();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case UtilConsts.StringConsts.LOG_IN_BUTTON_HAS_BEEN_PRESSED: {
                support.firePropertyChange(UtilConsts.EventTypesConsts.LOG_IN, null, event.getNewValue());
                break;
            }
            // TODO
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void displayChat() {
        menu.stopFrameDisplay();
        chatWindow.startFrameDisplay();
    }

    public void displayError(String error) {
        JOptionPane.showMessageDialog(null, error, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}
