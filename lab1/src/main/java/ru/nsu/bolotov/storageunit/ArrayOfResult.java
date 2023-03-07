package ru.nsu.bolotov.storageunit;

import ru.nsu.bolotov.container.Container;

import java.util.Arrays;
import java.util.Collections;

public class ArrayOfResult {
    private final StorageUnit[] array;

    public ArrayOfResult(Container container) {
        array = new StorageUnit[container.getContainer().size()];
        int index = 0;
        for (String key : container.getContainer().keySet()) {
            array[index] = new StorageUnit(key, container.getContainer().get(key), container.getTotalWordCounter());
            ++index;
        }
        Arrays.sort(array, Collections.reverseOrder());
    }

    public StorageUnit[] getArray() {
        return array;
    }
}
