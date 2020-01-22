package com.tsystems.javaschool.tasks.calculator;

public interface Visitor {
    public float visit(Operator operator);
    public float visit(Operand operand);
}
