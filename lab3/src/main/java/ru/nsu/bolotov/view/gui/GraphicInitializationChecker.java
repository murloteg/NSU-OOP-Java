package ru.nsu.bolotov.view.gui;

import ru.nsu.bolotov.view.InitializationChecker;

public class GraphicInitializationChecker implements InitializationChecker {
    private final GraphicView graphicView;
    private boolean statusOfInitialization;

    public GraphicInitializationChecker(GraphicView graphicView) {
        this.graphicView = graphicView;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            statusOfInitialization = graphicView.getStatusOfGameLaunch();
        }
    }

    @Override
    public boolean getInitializationStatus() {
        return statusOfInitialization;
    }
}
