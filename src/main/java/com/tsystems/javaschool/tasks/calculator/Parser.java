package com.tsystems.javaschool.tasks.calculator;

import java.util.EnumMap;

public class Parser {
    private final String statement;

    private enum States {
        INT,
        FLOAT,
        OPERAND,
        OPERATOR
    }

    private enum Brackets {OPEN, CLOSED};
    private static EnumMap<Brackets, Character> bracketToChar = new EnumMap<Brackets, Character>(Brackets.class) {{
        put(Brackets.OPEN, '(');
        put(Brackets.CLOSED, ')');
    }};

    private boolean areBracketsMatch() {
        int openBracketCounter = 0;
        for (char ch: this.statement.toCharArray()) {
            if (ch == bracketToChar.get(Brackets.CLOSED) && openBracketCounter == 0) {
                return false;
            } else if (ch == bracketToChar.get(Brackets.OPEN)) {
                openBracketCounter++;
            } else if (ch == bracketToChar.get(Brackets.CLOSED)) {
                openBracketCounter--;
            }
        }

        return openBracketCounter == 0;
    }

    public Parser(String statement) {

        this.statement = statement;
        if (!this.areBracketsMatch()) {
            throw new IllegalArgumentException();
        }
    }

    private Operator getFreeRightOperator(Operator op) {
        if (op.getRight() != null && op.getRight() instanceof Operator) {
            return getFreeRightOperator((Operator) op.getRight());
        } else if (op.getRight() == null) {
            return op;
        } else {
            throw new IllegalStateException();
        }
    }

    private Operator parse(String statement) {
        States localState = null;
        StringBuilder accum = new StringBuilder();
        Operator complexAccum = null;
        Operator cur = null;

        for (int i = 0; i < statement.length(); i++) {
            char currentChar = statement.charAt(i);
            if (localState == null) {
                if (currentChar == bracketToChar.get(Brackets.OPEN)) {
                    int closedBracketIndex = statement.lastIndexOf(bracketToChar.get(Brackets.CLOSED));
                    cur = parse(statement.substring(i+1, closedBracketIndex));
                    i = closedBracketIndex;
                    localState = States.OPERAND;
                } else if (Character.isDigit(currentChar)) {
                    accum.append(currentChar);
                    localState = States.INT;
                } else {
                    throw new IllegalStateException();
                }
            } else if (localState == States.INT) {
                if (Character.isDigit(currentChar)) {
                    accum.append(currentChar);
                } else if (currentChar == '.') {
                    accum.append(currentChar);
                    localState = States.FLOAT;
                } else if (Operator.isValidOperation(currentChar)) {
                    Operand en = new Operand(accum.toString(), Operand.Type.INT);
                    Operator en2 = new Operator(currentChar);
                    if (cur != null && cur.getPriority() < en2.getPriority()) {
                        en2.setLeft(en);
                        getFreeRightOperator(cur).setRight(en2);
                    } else if (cur != null && cur.getPriority() >= en2.getPriority()) {
                        getFreeRightOperator(cur).setRight(en);
                        en2.setLeft(cur);
                        cur = en2;
                    } else {
                        en2.setLeft(en);
                        cur = en2;
                    }
                    accum = new StringBuilder();
                    localState = States.OPERATOR;
                } else {
                    throw new IllegalStateException();
                }
            } else if (localState == States.FLOAT) {
                if (Character.isDigit(currentChar)) {
                    accum.append(currentChar);
                } else if (Operator.isValidOperation(currentChar)) {
                    Operand en = new Operand(accum.toString(), Operand.Type.INT);
                    Operator en2 = new Operator(currentChar);
                    if (cur != null && cur.getPriority() < en2.getPriority()) {
                        en2.setLeft(en);
                        getFreeRightOperator(cur).setRight(en2);
                    } else if (cur != null && cur.getPriority() >= en2.getPriority()) {
                        getFreeRightOperator(cur).setRight(en);
                        en2.setLeft(cur);
                        cur = en2;
                    } else {
                        en2.setLeft(en);
                    }
                    accum = new StringBuilder();
                    localState = States.OPERATOR;
                } else {
                    throw new IllegalStateException();
                }
            } else if (localState == States.OPERATOR) {
                if (Character.isDigit(currentChar)) {
                    accum.append(currentChar);
                    localState = States.INT;
                } else if (currentChar == bracketToChar.get(Brackets.OPEN)) {
                    int closedBracketIndex = statement.lastIndexOf(bracketToChar.get(Brackets.CLOSED));
                    complexAccum = parse(statement.substring(i+1, closedBracketIndex));
                    i = closedBracketIndex;
                    localState = States.OPERAND;
                } else {
                    throw new IllegalStateException();
                }
            } else if (localState == States.OPERAND) {
                if (Operator.isValidOperation(currentChar)) {
                    Operator en2 = new Operator(currentChar);
                    if (cur != null && cur.getPriority() < en2.getPriority()) {
                        en2.setLeft(complexAccum);
                        getFreeRightOperator(cur).setRight(en2);
                    } else if (cur != null && cur.getPriority() >= en2.getPriority()) {
                        getFreeRightOperator(cur).setRight(complexAccum);
                        en2.setLeft(cur);
                        cur = en2;
                    } else {
                        en2.setLeft(complexAccum);
                    }
            
                    localState = States.OPERATOR;
                } else {
                    throw new IllegalStateException();
                }
            } else {
                throw new IllegalStateException();
            }
        }

        if (cur != null && localState == States.INT) {
            Operand rightOperand = new Operand(accum.toString(), Operand.Type.INT);
            getFreeRightOperator(cur).setRight(rightOperand);
        } else if (cur != null && localState == States.FLOAT) {
            Operand rightOperand = new Operand(accum.toString(), Operand.Type.FLOAT);
            getFreeRightOperator(cur).setRight(rightOperand);
        } else {
            throw new IllegalArgumentException();
        }

        return cur;
    }

    public Operator parse() {
        return this.parse(this.statement);
    }
}
