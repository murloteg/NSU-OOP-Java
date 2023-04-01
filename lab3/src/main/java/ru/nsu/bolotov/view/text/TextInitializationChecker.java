package ru.nsu.bolotov.view.text;

import ru.nsu.bolotov.view.InitializationChecker;

public class TextInitializationChecker implements InitializationChecker {
    private final TextView textView;
    private boolean statusOfInitialization;

    public TextInitializationChecker(TextView graphicView) {
        this.textView = graphicView;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            statusOfInitialization = textView.getStatusOfGameLaunch();
        }
    }

    @Override
    public boolean getInitializationStatus() {
        return statusOfInitialization;
    }
}
