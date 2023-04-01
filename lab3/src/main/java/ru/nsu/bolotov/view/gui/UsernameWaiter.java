package ru.nsu.bolotov.view.gui;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class UsernameWaiter implements Runnable {
    private final JButton confirmButton;
    private boolean isButtonActivated;

    public UsernameWaiter(JButton confirmButton) {
        this.confirmButton = confirmButton;
        isButtonActivated = false;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(exception);
            }
            if (!confirmButton.isEnabled()) {
                isButtonActivated = true;
            }
        }
    }

    public boolean isButtonWasActivated() {
        return isButtonActivated;
    }
}
