package ru.nsu.bolotov.storageunit;


public class StorageUnit implements Comparable<StorageUnit> {
    public StorageUnit(String word, int frequency, int totalWordCounter) {
        this.word = word;
        this.frequency = frequency;
        this.totalWordCounter = totalWordCounter;
    }
    public String getWord() {
        return word;
    }
    public String getFrequency() {
        return "" + frequency;
    }
    public String getRatioInPercents() {
        return "" + String.format("%.4f",(double) frequency / totalWordCounter * 100) + "%";
    }
    private final String word;
    private final int frequency;
    private final int totalWordCounter;

    @Override
    public int compareTo(StorageUnit object) {
        return Integer.compare(this.frequency, object.frequency);
    }
}
