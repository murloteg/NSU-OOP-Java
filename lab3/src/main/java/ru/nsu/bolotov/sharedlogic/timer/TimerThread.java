package ru.nsu.bolotov.sharedlogic.timer;

import java.util.concurrent.TimeUnit;

public class TimerThread implements Runnable {
    private final long timeOfStartMillis;
    private long currentElapsedTimeSec;

    public TimerThread() {
        timeOfStartMillis = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            currentElapsedTimeSec = TimeUnit.SECONDS.convert(System.currentTimeMillis() - timeOfStartMillis, TimeUnit.MILLISECONDS);
        }
    }

    public long getCurrentTime() {
        return currentElapsedTimeSec;
    }
}
