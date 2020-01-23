package com.tsystems.javaschool.tasks.calculator;

public interface Visitor {
    public Operand visit(Operator operator);
    public Operand visit(Operand operand);
}
