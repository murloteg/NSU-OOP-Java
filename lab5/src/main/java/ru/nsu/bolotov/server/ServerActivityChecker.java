package ru.nsu.bolotov.server;

import ru.nsu.bolotov.utils.UtilConsts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;
import java.util.prefs.PreferenceChangeListener;

public class ServerActivityChecker implements Runnable {
    private final long startTimeMsec;
    private long currentTimeMsec;
    private final Server server;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ServerActivityChecker(Server server) {
        startTimeMsec = System.currentTimeMillis();
        this.server = server;
    }

    @Override
    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
//            if (server.getNumberOfUsers() == 0) {
//                currentTimeMsec = System.currentTimeMillis();
//            }
//            if (TimeUnit.MILLISECONDS.toSeconds(currentTimeMsec - startTimeMsec) >= UtilConsts.ConnectionConsts.WAITING_TIME_SEC) {
//                support.firePropertyChange(UtilConsts.StringConsts.INACTIVE_SERVER, false, true);
//                Thread.currentThread().interrupt();
//            }
//        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
