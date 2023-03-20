package ru.nsu.bolotov.sharedlogic.timer;

import java.util.concurrent.TimeUnit;

public class TimerThread implements Runnable {
    private final long timeOfStart;
    private long currentElapsedTimeInSec;

    public TimerThread() {
        timeOfStart = System.nanoTime();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long currentTime = System.nanoTime();
            currentElapsedTimeInSec = TimeUnit.SECONDS.convert(currentTime - timeOfStart, TimeUnit.NANOSECONDS);
        }
    }

    public long getCurrentTime() {
        return currentElapsedTimeInSec;
    }
}
