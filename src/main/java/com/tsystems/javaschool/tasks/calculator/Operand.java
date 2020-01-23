package com.tsystems.javaschool.tasks.calculator;

public class Operand implements ExpressionNode {


    public static enum Type {
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
