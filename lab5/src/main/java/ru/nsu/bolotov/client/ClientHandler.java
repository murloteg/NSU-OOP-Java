package ru.nsu.bolotov.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.event.Event;
import ru.nsu.bolotov.event.EventTypes;
import ru.nsu.bolotov.exceptions.FailedDeserializationException;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.utils.UtilConsts;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable {
    private static final List<ClientHandler> HANDLERS = new ArrayList<>();
    private static final List<String> MESSAGE_CACHE = new ArrayList<>();
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private String username;
    private final boolean loggingStatus;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    public ClientHandler(Socket clientSocket, boolean loggingStatus) throws IOException {
        this.clientSocket = clientSocket;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.loggingStatus = loggingStatus;
        username = UtilConsts.StringConsts.EMPTY_STRING;
        HANDLERS.add(this);
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
        closeAllResources();
    }

    private boolean isAuthorizedUser(ClientHandler clientHandler) {
        return !UtilConsts.StringConsts.EMPTY_STRING.equals(clientHandler.username);
    }

    private void sendEvent(ClientHandler clientHandler, Event event) throws IOException {
        clientHandler.outputStream.writeObject(event);
    }

    private void broadcastEvent(Event event) {
        for (ClientHandler clientHandler : HANDLERS) {
            try {
                if (isAuthorizedUser(clientHandler)) {
                    sendEvent(clientHandler, event);
                }
            } catch (IOException exception) {
                throw new IOBusinessException(exception.getMessage());
            }
        }
        if (EventTypes.MESSAGE.equals(event.getEventType())) {
            synchronized (MESSAGE_CACHE) {
                String message = event.getUsername() + ": " + event.getDescription();
                if (isMessageAlreadyInCache(message)) {
                    return;
                } else if (MESSAGE_CACHE.size() < UtilConsts.ConnectionConsts.MESSAGE_CACHE_SIZE) {
                    MESSAGE_CACHE.add(message);
                } else if (MESSAGE_CACHE.size() == UtilConsts.ConnectionConsts.MESSAGE_CACHE_SIZE) {
                    MESSAGE_CACHE.remove(0);
                    MESSAGE_CACHE.add(message);
                }
            }
        }
    }

    private boolean isMessageAlreadyInCache(String eventDescription) {
        return MESSAGE_CACHE.contains(eventDescription);
    }

    private String assemblyMessagesFromCache() {
        StringBuilder builder = new StringBuilder();
        for (String eventDescription : MESSAGE_CACHE) {
            builder.append(eventDescription).append('\n');
        }
        builder.append(String.format("[INFO] %d latest messages were displayed [INFO]", MESSAGE_CACHE.size()));
        return builder.toString();
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

    private boolean isCorrectUsername(String eventUsername) {
        Pattern pattern = Pattern.compile("^(\\w)*(\\w)$");
        return Pattern.matches(pattern.pattern(), eventUsername);
    }

    private boolean isAvailableUsername(String eventUsername) {
        for (ClientHandler clientHandler : HANDLERS) {
            if (eventUsername.equals(clientHandler.username)) {
                return false;
            }
        }
        if (!isCorrectUsername(eventUsername)) {
            return false;
        }
        this.username = eventUsername;
        return true;
    }

    private String makeUsersList() {
        StringBuilder builder = new StringBuilder();
        for (ClientHandler clientHandler : HANDLERS) {
            if (isAuthorizedUser(clientHandler)) {
                builder.append(clientHandler.username).append('\n');
            }
        }
        return builder.toString();
    }

    private void removeClientHandler(ClientHandler clientHandler) {
        HANDLERS.remove(clientHandler);
    }

    private void handleAuthorization(String eventUsername) throws IOException {
        if (isAvailableUsername(eventUsername)) {
            Event successfulAuthorizationEvent = new Event(EventTypes.SERVER_OK_RESPONSE, eventUsername, "Successful authorization!");
            outputStream.writeObject(successfulAuthorizationEvent);
            Event messagesCacheEvent = new Event(EventTypes.MESSAGE, UtilConsts.ConnectionConsts.SPECIAL_INFO_USERNAME, assemblyMessagesFromCache());
            sendEvent(this, messagesCacheEvent);
            Event connectionEvent = new Event(EventTypes.NEW_CONNECT, username, String.format("User with username \"%s\" successfully entered!", username));
            broadcastEvent(connectionEvent);
            Event usersListEvent = new Event(EventTypes.USERS_LIST, username, makeUsersList());
            broadcastEvent(usersListEvent);
        } else {
            Event failedAuthorizationEvent = new Event(EventTypes.SERVER_BAD_RESPONSE, eventUsername, String.format("Username \"%s\" already in use or contains invalid symbols", eventUsername));
            sendEvent(this, failedAuthorizationEvent);
        }
    }

    private void handleEvent(Event event) throws IOException {
        String eventUsername = event.getUsername();
        if (loggingStatus) {
            synchronized (LOGGER) {
                LOGGER.info("Handler received event with username \"{}\" and type \"{}\" ", eventUsername, event.getEventType());
            }
        }
        switch (event.getEventType()) {
            case LOG_IN: {
                handleAuthorization(eventUsername);
                break;
            }
            case MESSAGE: {
                broadcastEvent(event);
                break;
            }
            case DISCONNECT: {
                removeClientHandler(this);
                closeAllResources();
                Event disconnectEvent = new Event(EventTypes.DISCONNECT, username, String.format("User with username \"%s\" has left the chat!", username));
                broadcastEvent(disconnectEvent);
                Event usersListEvent = new Event(EventTypes.USERS_LIST, username, makeUsersList());
                broadcastEvent(usersListEvent);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unexpected event type");
            }
        }
    }
}
