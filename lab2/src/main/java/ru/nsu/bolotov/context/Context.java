package ru.nsu.bolotov.context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Context {
    private static final Map<String, Double> definitionsMap = new HashMap<>();
    private static final LinkedList<Double> stack = new LinkedList<>();

    public void addValueToStack(Double value) {
        stack.add(value);
    }

    public Double popValueFromStack() {
        return stack.removeLast();
    }

    public Double peekInStack() {
        return stack.peekLast();
    }

    public void addDefinitionToMap(String alias, Double value) {
        definitionsMap.put(alias, value);
    }

    public Double getDefinitionFromMap(String alias) {
        return definitionsMap.get(alias);
    }
}
