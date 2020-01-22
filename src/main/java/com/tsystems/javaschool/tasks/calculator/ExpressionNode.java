package com.tsystems.javaschool.tasks.calculator;

public interface ExpressionNode {
    public void accept(Visitor visitor);
}