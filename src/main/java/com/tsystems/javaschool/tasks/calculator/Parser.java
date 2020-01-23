package com.tsystems.javaschool.tasks.calculator;

import java.util.Arrays;
import java.util.HashSet;

public class Parser {
    private final String statement;

    private enum States {
        INT,
        FLOAT,
        OPERAND,
        OPERATOR
    }

    HashSet<Character> validOperators = new HashSet<>(Arrays.asList('+', '-', '*', '/'));

    public Parser(String statement) {
        this.statement = statement.trim();
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
        Operator accum2 = null;
        Operator cur = null;
        for (int i = 0; i < statement.length(); i++) {
            char currentChar = statement.charAt(i);
            if (localState == null) {
                if (currentChar == '(') {
                    int closedBracketIndex = statement.lastIndexOf(')');
                    cur = parse(statement.substring(i+1, closedBracketIndex));
                    i = closedBracketIndex;
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
                } else if (validOperators.contains(currentChar)) {
                    Operand en = new Operand(accum.toString(), Operand.Type.INT);
                    Operator en2 = new Operator(Character.toString(currentChar));
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
                } else if (validOperators.contains(currentChar)) {
                    Operand en = new Operand(accum.toString(), Operand.Type.INT);
                    Operator en2 = new Operator(Character.toString(currentChar));
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
                } else if (currentChar == '(') {
                    int closedBracketIndex = statement.lastIndexOf(')');
                    accum2 = parse(statement.substring(i+1, closedBracketIndex));
                    i = closedBracketIndex;
                    localState = States.OPERAND;
                } else {
                    throw new IllegalStateException();
                }
            } else if (localState == States.OPERAND) {
                if (validOperators.contains(currentChar)) {
                    Operator en2 = new Operator(Character.toString(currentChar));
                    if (cur != null && cur.getPriority() < en2.getPriority()) {
                        en2.setLeft(accum2);
                        getFreeRightOperator(cur).setRight(en2);
                    } else if (cur != null && cur.getPriority() >= en2.getPriority()) {
                        getFreeRightOperator(cur).setRight(accum2);
                        en2.setLeft(cur);
                        cur = en2;
                    } else {
                        en2.setLeft(accum2);
                    }
            
                    localState = States.OPERATOR;
                } else {
                    throw new IllegalStateException();
                }
            } else {
                throw new IllegalStateException();
            }
        }

        if (localState == States.INT) {
            Operand en = new Operand(accum.toString(), Operand.Type.INT);
            getFreeRightOperator(cur).setRight(en);
        } else if (localState == States.FLOAT) {
            Operand en = new Operand(accum.toString(), Operand.Type.FLOAT);
            getFreeRightOperator(cur).setRight(en);
        }
        return cur;
    }


    public Operator parse() {
        return this.parse(this.statement);
    }
}
