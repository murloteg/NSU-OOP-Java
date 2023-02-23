package ru.nsu.bolotov.storageunit;

import ru.nsu.bolotov.container.*;
import java.util.*;

public class ArrayOfResult {
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
    private final StorageUnit[] array;
}
