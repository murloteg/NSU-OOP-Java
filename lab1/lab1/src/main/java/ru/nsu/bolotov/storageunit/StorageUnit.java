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

    @Override
    public int compareTo(StorageUnit object) {
        return Integer.compare(this.frequency, object.frequency);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    private final String word;
    private final int frequency;
    private final int totalWordCounter;
}
