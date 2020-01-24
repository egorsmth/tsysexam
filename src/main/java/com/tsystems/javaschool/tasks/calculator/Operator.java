package com.tsystems.javaschool.tasks.calculator;

import java.util.*;

public class Operator implements ExpressionNode {
    private enum Operators {ADD, SUB, MUL, DIV};
    private static EnumMap<Operators, Character> operatorsToChar = new EnumMap<Operators, Character>(Operators.class) {{
        put(Operators.ADD, '+');
        put(Operators.SUB, '-');
        put(Operators.MUL, '*');
        put(Operators.DIV, '/');
    }};

    private static HashSet<Character> validOperators = new HashSet<>(
            Arrays.asList(
                    operatorsToChar.get(Operators.ADD),
                    operatorsToChar.get(Operators.SUB),
                    operatorsToChar.get(Operators.MUL),
                    operatorsToChar.get(Operators.DIV)
            )
    );

    public static boolean isValidOperation(char op) {
        return validOperators.contains(op);
    }

    private ExpressionNode left;
    private ExpressionNode right;

    private static Map<Character, Integer> operationPriorities = new HashMap<Character, Integer>() {{
        put(operatorsToChar.get(Operators.ADD), 1);
        put(operatorsToChar.get(Operators.SUB), 1);
        put(operatorsToChar.get(Operators.MUL), 2);
        put(operatorsToChar.get(Operators.DIV), 2);
    }};

    private char value;
    private int priority;

    public Operator(char op) throws IllegalArgumentException {
        if (!isValidOperation(op)) {
            throw new IllegalArgumentException("No such operator: " + op);
        }

        this.priority = operationPriorities.get(op);
        this.value = op;
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

    public Operand operation(int a, int b) {
        Operand result;

        if (this.value == operatorsToChar.get(Operators.ADD)) {
            result = new Operand(Integer.toString(a + b), Operand.Type.INT);

        } else if (this.value == operatorsToChar.get(Operators.SUB)) {
            result = new Operand(Integer.toString(a - b), Operand.Type.INT);

        } else if (this.value == operatorsToChar.get(Operators.MUL)) {
            result = new Operand(Integer.toString(a * b), Operand.Type.INT);

        } else if (this.value == operatorsToChar.get(Operators.DIV)) {
            if (b == 0)
                throw new IllegalArgumentException();
            result = new Operand(Float.toString((float) a / b), Operand.Type.FLOAT);

        } else {
            throw new IllegalArgumentException();
        }

        return result;
    }

    public Operand operation(float a, int b) {
        Operand result;

        if (this.value == operatorsToChar.get(Operators.ADD)) {
            result = new Operand(Float.toString(a + b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.SUB)) {
            result = new Operand(Float.toString(a - b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.MUL)) {
            result = new Operand(Float.toString(a * b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.DIV)) {
            if (b == 0)
                throw new IllegalArgumentException();
            result = new Operand(Float.toString(a / b), Operand.Type.FLOAT);

        } else {
            throw new IllegalArgumentException();
        }

        return result;
    }

    public Operand operation(float a, float b) {
        Operand result;

        if (this.value == operatorsToChar.get(Operators.ADD)) {
            result = new Operand(Float.toString(a + b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.SUB)) {
            result = new Operand(Float.toString(a - b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.MUL)) {
            result = new Operand(Float.toString(a * b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.DIV)) {
            if (b == 0)
                throw new IllegalArgumentException();
            result = new Operand(Float.toString(a / b), Operand.Type.FLOAT);

        } else {
            throw new IllegalArgumentException();
        }

        return result;
    }

    public Operand operation(int a, float b) {
        Operand result;
        if (this.value == operatorsToChar.get(Operators.ADD)) {
            result = new Operand(Float.toString(a + b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.SUB)) {
            result = new Operand(Float.toString(a - b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.MUL)) {
            result = new Operand(Float.toString(a * b), Operand.Type.FLOAT);

        } else if (this.value == operatorsToChar.get(Operators.DIV)) {
            if (b == 0)
                throw new IllegalArgumentException();
            result = new Operand(Float.toString(a / b), Operand.Type.FLOAT);

        } else {
            throw new IllegalArgumentException();
        }

        return result;

    }
}
