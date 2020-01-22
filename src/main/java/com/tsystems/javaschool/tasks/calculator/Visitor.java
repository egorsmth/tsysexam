package com.tsystems.javaschool.tasks.calculator;

public interface Visitor {
    public void visit(Operator operator);
    public void visit(Operand operand);
}
