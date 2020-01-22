package com.tsystems.javaschool.tasks.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Operator implements ExpressionNode {
    private ExpressionNode left;
    private ExpressionNode right;

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

    public void setLeft(ExpressionNode node) {
        left = node;
    }

    public void setRight(ExpressionNode node) {
        right = node;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public ExpressionNode getLeft() {
        return left;
    }
}
