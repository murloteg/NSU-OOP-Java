package ru.nsu.bolotov.client;

import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.parser.ConfigurationParser;
import ru.nsu.bolotov.utils.UtilConsts;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final Socket clientSocket;

    public Client(short port) throws IOException {
        clientSocket = new Socket(UtilConsts.ConnectionConsts.IP_ADDR, port);
    }

    public static void main(String[] args) {
        short port = ConfigurationParser.getPort();
        Client client;
        try {
            client = new Client(port);
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
    }
}
