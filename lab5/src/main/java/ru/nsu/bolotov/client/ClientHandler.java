package ru.nsu.bolotov.client;

import ru.nsu.bolotov.event.Event;
import ru.nsu.bolotov.exceptions.IOBusinessException;

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

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        HANDLERS.add(this);
    }

    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                Event event = (Event) inputStream.readObject();
                System.out.println("Accept: " + event.getEventType());
                broadcastEventToOtherClients(event);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            } catch (ClassNotFoundException exception) {
                throw new RuntimeException(exception); // FIXME
            }
        }
        closeAllResources();
    }

    public void broadcastEventToOtherClients(Event event) {
        for (ClientHandler clientHandler : HANDLERS) {
            try {
                clientHandler.outputStream.writeObject(event);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
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
}
