package ru.nsu.bolotov.client;

import java.io.IOException;
import java.net.Socket;

public class UserData {
    private final Socket clientSocket;

    public UserData(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void closeSocket() throws IOException {
        clientSocket.close();
    }
}
