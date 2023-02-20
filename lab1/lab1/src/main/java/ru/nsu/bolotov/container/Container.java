package ru.nsu.bolotov.container;

import java.util.*;

public class Container {
    public Container() {
        container = new HashMap<>();
    }
    public void addWordToMap(String currentWord) {
        int frequency = 1;
        if (container.containsKey(currentWord)) {
            frequency = container.get(currentWord);
            container.replace(currentWord, frequency + 1);
        }
        else {
            container.put(currentWord, frequency);
        }
        ++totalWordCounter;
    }
    public HashMap<String, Integer> getContainer() {
        return container;
    }
    public int getTotalWordCounter() {
        return totalWordCounter;
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String key : container.keySet()) {
            result.append(key).append(" : ").append(container.get(key)).append("\n");
        }
        result.append("Total counter: ").append(totalWordCounter);
        return result.toString();
    }
    private final HashMap<String, Integer> container;
    private int totalWordCounter = 0;
}
