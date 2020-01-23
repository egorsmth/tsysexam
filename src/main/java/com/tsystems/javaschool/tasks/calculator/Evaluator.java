package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;

public class Evaluator implements Visitor {
    Operator op;
    private static DecimalFormat df = new DecimalFormat("0.0###");
    public Evaluator(Operator op) {
        this.op = op;
    }

    public String evaluate() {
        Operand res = visit(this.op);
        return res.getType() == Operand.Type.FLOAT ? df.format(Float.parseFloat(res.getValue())) : res.getValue();
    }

    private Operand performOperation(Operator operation, Operand left, Operand right) {
        if (left.getType() == Operand.Type.INT && right.getType() == Operand.Type.INT) {
            return operation.operation(Integer.parseInt(left.getValue()), Integer.parseInt(right.getValue()));

        } else if (left.getType() == Operand.Type.FLOAT && right.getType() == Operand.Type.INT) {
            return operation.operation(Float.parseFloat(left.getValue()), Integer.parseInt(right.getValue()));

        } else if (left.getType() == Operand.Type.FLOAT && right.getType() == Operand.Type.FLOAT) {
            return operation.operation(Float.parseFloat(left.getValue()), Float.parseFloat(right.getValue()));

        } else if (left.getType() == Operand.Type.INT && right.getType() == Operand.Type.FLOAT) {
            return operation.operation(Integer.parseInt(left.getValue()), Float.parseFloat(right.getValue()));

        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Operand visit(Operator operator) {

        ExpressionNode l = operator.getLeft();
        ExpressionNode r = operator.getRight();

        if (l instanceof  Operand && r instanceof Operand) {
            return performOperation(operator, this.visit((Operand) l), this.visit((Operand) r));
        } else if (l instanceof  Operand && r instanceof Operator) {
            return performOperation(operator, this.visit((Operand) l), this.visit((Operator) r));
        } else if (l instanceof  Operator && r instanceof Operand) {
            return performOperation(operator, this.visit((Operator) l), this.visit((Operand) r));
        } else if (l instanceof  Operator && r instanceof Operator) {
            return performOperation(operator, this.visit((Operator) l), this.visit((Operator) r));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public Operand visit(Operand operand) {
        return operand;
    }
}
