package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;

public class Evaluator {
    Operator op;
    private DecimalFormat decimalFormat = new DecimalFormat("0.0###");
    public Evaluator(Operator op) {
        this.op = op;
    }

    private boolean couldBeInt(float a) {
        return a == (float) Math.round(a);
    }

    public String compute() {
        Operand result = visit(this.op);

        if (result.getType() == Operand.Type.FLOAT && couldBeInt(Float.parseFloat(result.getValue()))) {
            return Integer.toString((int)Float.parseFloat(result.getValue()));
        } else if (result.getType() == Operand.Type.FLOAT) {
            return decimalFormat.format(Float.parseFloat(result.getValue()));
        }
        return result.getValue();
    }

    private Operand performOperation(Operator operation, Operand left, Operand right) {
        Operand.Type leftType = left.getType();
        Operand.Type rightType = right.getType();

        if (leftType == Operand.Type.INT && rightType == Operand.Type.INT) {
            return operation.operation(Integer.parseInt(left.getValue()), Integer.parseInt(right.getValue()));

        } else if (leftType == Operand.Type.FLOAT && rightType == Operand.Type.INT) {
            return operation.operation(Float.parseFloat(left.getValue()), Integer.parseInt(right.getValue()));

        } else if (leftType == Operand.Type.FLOAT && rightType == Operand.Type.FLOAT) {
            return operation.operation(Float.parseFloat(left.getValue()), Float.parseFloat(right.getValue()));

        } else if (leftType == Operand.Type.INT && rightType == Operand.Type.FLOAT) {
            return operation.operation(Integer.parseInt(left.getValue()), Float.parseFloat(right.getValue()));

        } else {
            throw new IllegalArgumentException();
        }
    }

    public Operand visit(Operator operator) {
        ExpressionNode left = operator.getLeft();
        ExpressionNode right = operator.getRight();

        if (left instanceof  Operand && right instanceof Operand) {
            return performOperation(operator, (Operand) left, (Operand) right);

        } else if (left instanceof  Operand && right instanceof Operator) {
            return performOperation(operator, (Operand) left, this.visit((Operator) right));

        } else if (left instanceof  Operator && right instanceof Operand) {
            return performOperation(operator, this.visit((Operator) left), (Operand) right);

        } else if (left instanceof  Operator && right instanceof Operator) {
            return performOperation(operator, this.visit((Operator) left), this.visit((Operator) right));

        } else {
            throw new IllegalStateException();
        }
    }
}
