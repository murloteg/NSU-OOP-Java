package ru.nsu.bolotov.view.gui;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GameClosedChecker implements Runnable {
    private final Optional<GraphicView> graphicView;
    private boolean isGameClosed;

    public GameClosedChecker(Optional<GraphicView> graphicView) {
        this.graphicView = graphicView;
        isGameClosed = false;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(exception);
            }
            graphicView.ifPresent(view -> isGameClosed = !view.isGameLaunched());
        }
    }

    public boolean isGameClosed() {
        return isGameClosed;
    }
}
