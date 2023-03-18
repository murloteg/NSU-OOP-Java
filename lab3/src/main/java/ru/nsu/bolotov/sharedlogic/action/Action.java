package ru.nsu.bolotov.sharedlogic.action;

import java.util.ArrayList;

public interface Action {
    void doAction(ArrayList<Object> args);
    void checkNumberOfArguments(ArrayList<Object> args);
}
