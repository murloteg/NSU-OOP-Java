package ru.nsu.bolotov.storageunit;

import ru.nsu.bolotov.container.*;

import java.util.Arrays;
import java.util.Collections;

public class ArrayOfResult {
    public StorageUnit[] getArray() {
        return array;
    }

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
}
