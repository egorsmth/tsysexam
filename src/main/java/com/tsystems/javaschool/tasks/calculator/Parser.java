package com.tsystems.javaschool.tasks.calculator;

public class Parser {
    private final String statement;

    private States currentState;

    private enum States {
        INT,
        FLOAT,
        OPERATOR
    }

    public Parser(String statement) {
        this.statement = statement.trim();
    }

    private void handleState(States cur, char ch) {

    }

    private void init() {
        if (Character.isDigit(this.statement.charAt(0))) {
            currentState = States.INT;
        } else if (this.statement.charAt(0) == '('){
            int i = 1;
            while (this.statement.charAt(i) != '(') i++;
            if (Character.isDigit(this.statement.charAt(i))) {
                currentState = States.INT;
            }
        } else {
            throw new IllegalArgumentException("Parsing exception");
        }
    }

    public void parse() {
        ExpressionNode current;
        init();
        StringBuilder accum = new StringBuilder();
        for (char item: this.statement.toCharArray()) {
            if (currentState == States.INT) {
                if (Character.isDigit(item)) {
                    accum.append(item);
                } else if (item == '.') {
                    accum.append(item);
                    currentState = States.FLOAT;
                }
            } else if (currentState == States.FLOAT) {

            } else if (currentState == States.OPERATOR) {

            } else {
                throw new IllegalStateException();
            }
        }
    }
}
