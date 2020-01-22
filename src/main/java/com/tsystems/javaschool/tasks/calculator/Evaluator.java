package com.tsystems.javaschool.tasks.calculator;

public class Evaluator implements Visitor {
    Operator op;
    public Evaluator(Operator op) {
        this.op = op;
    }

    public float evaluate() {
        return visit(this.op);
    }

    private float performOperation(String operation, float left, float right) {
        float res;
        switch (operation) {
            case "+":
                res = left + right;
                break;
            case "-":
                res = left - right;
                break;
            case "*":
                res = left * right;
                break;
            case "/":
                res = left / right;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return res;
    }

    @Override
    public float visit(Operator operator) {

        ExpressionNode l = operator.getLeft();
        ExpressionNode r = operator.getRight();

        if (l instanceof  Operand && r instanceof Operand) {
            return performOperation(operator.getValue(), this.visit((Operand) l), this.visit((Operand) r));
        } else if (l instanceof  Operand && r instanceof Operator) {
            return performOperation(operator.getValue(), this.visit((Operand) l), this.visit((Operator) r));
        } else if (l instanceof  Operator && r instanceof Operand) {
            return performOperation(operator.getValue(), this.visit((Operator) l), this.visit((Operand) r));
        } else if (l instanceof  Operator && r instanceof Operator) {
            return performOperation(operator.getValue(), this.visit((Operator) l), this.visit((Operator) r));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public float visit(Operand operand) {
        return Float.parseFloat(operand.getValue());
    }
}
