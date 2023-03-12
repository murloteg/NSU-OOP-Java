package ru.nsu.bolotov.application;

import ru.nsu.bolotov.sharedclasses.field.*;

public class Main {
    public static void main(String[] args) {
        Field field = new Field();
        FillerOfField.fillWithNumbers(field);
        field.printField();
    }
}
