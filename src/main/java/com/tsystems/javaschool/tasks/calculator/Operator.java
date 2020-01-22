package com.tsystems.javaschool.tasks.calculator;

import java.util.HashMap;
import java.util.Map;

public class Operator implements ExpressionNode {

    private static Map<String, Integer> operationPriorities = new HashMap<String, Integer>() {{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
    }};

    private String value;
    private int priority;

    public Operator(String op) throws IllegalArgumentException {
        if (!operationPriorities.containsKey(op)) {
            throw new IllegalArgumentException("No operator: " + op);
        }

        this.priority = operationPriorities.get(op);
        this.value = op;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }
}
