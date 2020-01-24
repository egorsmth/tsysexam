package com.tsystems.javaschool.tasks.calculator;

public class Operand implements ExpressionNode {
    public enum Type {
        FLOAT,
        INT
    }

    private Type type;
    private String value;

    public Operand(String op, Type type)
    {
        this.value = op;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
