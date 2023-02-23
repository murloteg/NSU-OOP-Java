package ru.nsu.bolotov.container;

import java.util.*;

public class Container {
    public void addWordToMap(String currentWord) {
        int frequency = 1;
        if (dataMap.containsKey(currentWord)) {
            frequency = dataMap.get(currentWord);
            dataMap.replace(currentWord, frequency + 1);
        }
        else {
            dataMap.put(currentWord, frequency);
        }
        ++totalWordCounter;
    }

    public Map<String, Integer> getContainer() {
        return dataMap;
    }

    public int getTotalWordCounter() {
        return totalWordCounter;
    }
    private final HashMap<String, Integer> dataMap = new HashMap<>();
    private int totalWordCounter = 0;
}
