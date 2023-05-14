package ru.nsu.bolotov.view;

public class UserWaiter implements Runnable {
    private boolean isApplicationLaunched;

    public UserWaiter() {
        this.isApplicationLaunched = false;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isApplicationLaunched) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean getStatusOfLaunch() {
        return isApplicationLaunched;
    }

    public void setStatusOfLaunch(boolean statusOfLaunch) {
        isApplicationLaunched = statusOfLaunch;
    }
}
