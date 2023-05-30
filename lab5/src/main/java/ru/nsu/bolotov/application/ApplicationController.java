package ru.nsu.bolotov.application;

import ru.nsu.bolotov.client.Client;
import ru.nsu.bolotov.exceptions.IOBusinessException;
import ru.nsu.bolotov.parser.CmdLineParser;
import ru.nsu.bolotov.parser.ConfigurationParser;
import ru.nsu.bolotov.server.Server;
import ru.nsu.bolotov.server.ServerActivityChecker;
import ru.nsu.bolotov.utils.UtilConsts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Optional;

public class ApplicationController implements PropertyChangeListener {
    private Optional<Thread> serverThread;

    public ApplicationController() {
        serverThread = Optional.empty();
    }

    public void startApplication(String[] args) {
        CmdLineParser cmdLineParser = new CmdLineParser();
        cmdLineParser.parseCmdLine(args);

        short port = ConfigurationParser.getPort();
        boolean loggingStatus = ConfigurationParser.getLoggingStatus();
        String type = cmdLineParser.getType().toUpperCase();
        switch (type) {
            case UtilConsts.StringConsts.SERVER: {
                prepareServer(port, loggingStatus);
                break;
            }
            case UtilConsts.StringConsts.CLIENT: {
                prepareClient(port);
                break;
            }
            default: {

            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case UtilConsts.StringConsts.INACTIVE_SERVER: {
                serverThread.ifPresent(Thread::interrupt);

                break;
            }
            default: {

            }
        }
    }

    private void prepareServer(short port, boolean loggingStatus) {
        try {
            Server server = new Server(port, loggingStatus);
//            serverThread = Optional.of(new Thread(server));
            serverThread.ifPresent(Thread::start);

            ServerActivityChecker activityChecker = new ServerActivityChecker(server);
            activityChecker.addPropertyChangeListener(this);
            Thread activityCheckerThread = new Thread(activityChecker);
            activityCheckerThread.start();
            // TODO:
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
    }

    private void prepareClient(short port) {
        try {
            Client client = new Client(port);
            // TODO:
        } catch (IOException exception) {
            throw new IOBusinessException(exception.getMessage());
        }
    }
}
