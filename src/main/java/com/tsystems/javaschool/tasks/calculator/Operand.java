package com.tsystems.javaschool.tasks.calculator;

public class Operand implements ExpressionNode {

    private ExpressionNode left;
    private ExpressionNode right;

    private enum Type {
        FLOAT,
        INT
    }
    private final Type type;
    private final String value;

    public Operand(String op, Type type)
    {
        this.value = op;
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
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

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
