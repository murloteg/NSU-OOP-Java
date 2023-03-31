package ru.nsu.bolotov.gui;

public class GraphicInitializationChecker implements Runnable {
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

    public boolean getInitializationStatus() {
        return statusOfInitialization;
    }
}
