package ru.nsu.bolotov.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.bolotov.client.UserData;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.parser.ConfigurationParser;
import ru.nsu.bolotov.utils.UtilConsts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server implements PropertyChangeListener {
    private final ServerSocket serverSocket;
    private final List<UserData> clients;
    private final boolean loggingStatus;
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public Server(short port, boolean loggingStatus) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new LinkedList<>();
        this.loggingStatus = loggingStatus;
        if (loggingStatus) {
            LOGGER.info("Server listens port {}", serverSocket.getLocalPort());
        }
    }

    public void launchServer() {
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException exception) {
                if (loggingStatus) {
                    LOGGER.error(exception.getMessage());
                }
                throw new IOBusinessException(exception.getMessage());
            }
            clients.add(new UserData(clientSocket));
            if (loggingStatus) {
                LOGGER.info("Server accepted new connection");
            }
        }
    }

    public int getNumberOfUsers() {
        return clients.size();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case UtilConsts.StringConsts.INACTIVE_SERVER: {
                closeAllResources();
                break;
            }
            // TODO
            default: {

            }
        }
    }

    private void closeAllResources() {
        try {
            serverSocket.close();
            for (UserData user : clients) {
                user.closeSocket();
            }
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
        ServerActivityChecker activityChecker = new ServerActivityChecker(server);
        activityChecker.addPropertyChangeListener(server);
        Thread activityCheckerThread = new Thread(activityChecker);
        activityCheckerThread.start();
        server.launchServer();
    }
}
