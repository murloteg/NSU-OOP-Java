package ru.nsu.bolotov.client;

import ru.nsu.bolotov.event.Event;
import ru.nsu.bolotov.event.EventTypes;
import ru.nsu.bolotov.exceptions.FailedDeserializationException;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.parser.ConfigurationParser;
import ru.nsu.bolotov.utils.UtilConsts;
import ru.nsu.bolotov.view.ApplicationView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable, PropertyChangeListener {
    private final Socket clientSocket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final ApplicationView view;
    private String username;

    public Client(short port) throws IOException {
        clientSocket = new Socket(UtilConsts.ConnectionConsts.IP_ADDR, port);
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        view = new ApplicationView();
        view.addPropertyChangeListener(this);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && clientSocket.isConnected()) {
            try {
                Event event = (Event) inputStream.readObject();
                handleEvent(event);
            } catch (IOException exception) {
                throw new IOBusinessException(exception.getMessage());
            } catch (ClassNotFoundException exception) {
                throw new FailedDeserializationException(exception.getMessage());
            }
        }
    }

    private void handleEvent(Event event) {
        String eventUsername = event.getUsername();
        String eventDescription = event.getDescription();
        switch (event.getEventType()) {
            case SERVER_OK_RESPONSE: {
                username = eventUsername;
                view.displayChat();
                view.displayCurrentUser(username);
                break;
            }
            case SERVER_BAD_RESPONSE: {
                view.displayError(eventDescription);
                break;
            }
            case NEW_CONNECT:
            case DISCONNECT: {
                view.displayEventMessage(eventDescription);
                break;
            }
            case USERS_LIST: {
                view.displayUsersList(eventDescription);
                break;
            }
            case MESSAGE: {
                if (UtilConsts.ConnectionConsts.SPECIAL_INFO_USERNAME.equals(eventUsername)) {
                    view.displayEventMessage(eventDescription);
                } else {
                    view.displayEventMessage(eventUsername + ": " + eventDescription);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException(UtilConsts.StringConsts.UNEXPECTED_EVENT_TYPE);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case UtilConsts.EventTypesConsts.LOG_IN: {
                username = (String) event.getNewValue();
                Event loginEvent = new Event(EventTypes.LOG_IN, username, null);
                try {
                    outputStream.writeObject(loginEvent);
                } catch (IOException exception) {
                    throw new IOBusinessException(exception.getMessage());
                }
                break;
            }
            case UtilConsts.EventTypesConsts.MESSAGE: {
                String message = (String) event.getNewValue();
                Event messageEvent = new Event(EventTypes.MESSAGE, username, message);
                try {
                    outputStream.writeObject(messageEvent);
                } catch (IOException exception) {
                    throw new IOBusinessException(exception.getMessage());
                }
                break;
            }
            case UtilConsts.EventTypesConsts.DISCONNECT: {
                Event disconnectEvent = new Event(EventTypes.DISCONNECT, username, null);
                try {
                    outputStream.writeObject(disconnectEvent);
                    closeAllResources();
                } catch (IOException exception) {
                    throw new IOBusinessException(exception.getMessage());
                }
                break;
            }
            default: {
                throw new IllegalArgumentException(UtilConsts.StringConsts.UNEXPECTED_EVENT_TYPE);
            }
        }
    }

    private void closeAllResources() {
        try {
            Thread.currentThread().interrupt();
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        short port = ConfigurationParser.getPort();
        Client client;
        try {
            client = new Client(port);
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }

        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
