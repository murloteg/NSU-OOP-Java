package ru.nsu.bolotov.client;

import ru.nsu.bolotov.event.Event;
import ru.nsu.bolotov.event.EventTypes;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.utils.UtilConsts;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final List<ClientHandler> HANDLERS = new ArrayList<>();
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private String username;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        username = UtilConsts.StringConsts.EMPTY_STRING;
        HANDLERS.add(this);
    }

    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                Event event = (Event) inputStream.readObject();
                handleEvent(event);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            } catch (ClassNotFoundException exception) {
                throw new RuntimeException(exception); // FIXME
            }
        }
        closeAllResources();
    }

    public void broadcastEvent(Event event) {
        for (ClientHandler clientHandler : HANDLERS) {
            try {
                clientHandler.outputStream.writeObject(event);
            } catch (IOException exception) {
                throw new RuntimeException(exception); // FIXME
            }
        }
    }

    public int getNumberOfClients() {
        return HANDLERS.size();
    }

    private void closeAllResources() {
        try {
            for (ClientHandler clientHandler : HANDLERS) {
                clientHandler.clientSocket.close();
                clientHandler.outputStream.close();
                clientHandler.inputStream.close();
            }
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
    }

    private boolean isAvailableUsername(String eventUsername) {
        for (ClientHandler clientHandler : HANDLERS) {
            if (eventUsername.equals(clientHandler.username)) {
                return false;
            }
        }
        this.username = eventUsername;
        return true;
    }

    private String makeUsersList() {
        StringBuilder builder = new StringBuilder();
        for (ClientHandler clientHandler : HANDLERS) {
            builder.append(clientHandler.username).append('\n');
        }
        return builder.toString();
    }

    private void handleEvent(Event event) throws IOException {
        String eventUsername = event.getUsername();
        switch (event.getEventType()) {
            case LOG_IN: {
                if (isAvailableUsername(eventUsername)) {
                    Event successfulAuthorizationEvent = new Event(EventTypes.SERVER_OK_RESPONSE, eventUsername, "Successful authorization!");
                    outputStream.writeObject(successfulAuthorizationEvent);
                    Event connectionEvent = new Event(EventTypes.NEW_CONNECT, username, String.format("User with username \"%s\" successfully entered!", username));
                    broadcastEvent(connectionEvent);
                    Event usersListEvent = new Event(EventTypes.USERS_LIST, username, makeUsersList());
                    broadcastEvent(usersListEvent);
                } else {
                    Event failedAuthorizationEvent = new Event(EventTypes.SERVER_BAD_RESPONSE, eventUsername, String.format("Username \"%s\" already in use!", eventUsername));
                    outputStream.writeObject(failedAuthorizationEvent);
                }
                break;
            }
            case MESSAGE: {
                broadcastEvent(event);
                break;
            }
            case DISCONNECT: {
                // ...
                Event usersListEvent = new Event(EventTypes.USERS_LIST, username, makeUsersList());
                broadcastEvent(usersListEvent);
                break;
            }
            default: {
                // TODO
            }
        }
    }
}
