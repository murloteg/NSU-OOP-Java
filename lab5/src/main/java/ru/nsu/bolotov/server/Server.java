package ru.nsu.bolotov.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.client.ClientHandler;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.parser.ConfigurationParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private final boolean loggingStatus;
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public Server(short port, boolean loggingStatus) throws IOException {
        serverSocket = new ServerSocket(port);
        this.loggingStatus = loggingStatus;
        if (loggingStatus) {
            LOGGER.info("Server listens port {}", serverSocket.getLocalPort());
        }
    }

    public void launchServer() {
        while (!serverSocket.isClosed()) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread handlerThread = new Thread(clientHandler);
                handlerThread.start();
            } catch (IOException exception) {
                if (loggingStatus) {
                    LOGGER.error(exception.getMessage());
                }
                throw new IOBusinessException(exception.getMessage());
            }
            if (loggingStatus) {
                LOGGER.info("Server accepted new connection");
            }
        }
        closeAllResources();
    }

    private void closeAllResources() {
        try {
            serverSocket.close();
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        short port = ConfigurationParser.getPort();
        boolean loggingStatus = ConfigurationParser.getLoggingStatus();
        Server server;
        try {
            server = new Server(port, loggingStatus);
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
        server.launchServer();
    }
}
