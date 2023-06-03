package ru.nsu.bolotov.client;

import ru.nsu.bolotov.event.Event;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.parser.ConfigurationParser;
import ru.nsu.bolotov.utils.UtilConsts;
import ru.nsu.bolotov.view.ApplicationView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private final Socket clientSocket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final ApplicationView view;

    public Client(short port) throws IOException {
        clientSocket = new Socket(UtilConsts.ConnectionConsts.IP_ADDR, port);
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        view = new ApplicationView(); // TODO
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeObject(new Event(message));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Event event;
            while (clientSocket.isConnected()) {
                try {
                    event = (Event) inputStream.readObject();
                    System.out.println("Received event: " + event.getEventType());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
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
        Scanner scanner = new Scanner(System.in);
        client.sendMessage(scanner.nextLine());
        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
