package ru.nsu.bolotov.client;

import ru.nsu.bolotov.event.Event;
import ru.nsu.bolotov.event.EventTypes;
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
        view = new ApplicationView(); // TODO
        view.addPropertyChangeListener(this);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Event event;
            while (clientSocket.isConnected()) {
                try {
                    event = (Event) inputStream.readObject();
                    System.out.println("Received event: " + event.getDescription()); // TODO
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
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
                } catch (IOException e) { // FIXME
                    throw new RuntimeException(e);
                }
                break;
            }
            case UtilConsts.EventTypesConsts.MESSAGE: {
                // TODO
                break;
            }
            case UtilConsts.EventTypesConsts.DISCONNECT: {
                // TODO
                break;
            }
            default: {
                // TODO
            }
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
