package ru.nsu.bolotov.view;

public interface InitializationChecker extends Runnable {
    boolean getInitializationStatus();
}
