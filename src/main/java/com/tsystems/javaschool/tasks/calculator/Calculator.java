package com.tsystems.javaschool.tasks.calculator;

import java.util.Arrays;
import java.util.HashSet;

public class Calculator {

    private String statement;
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        if (statement == null) {
            return null;
        }

        this.statement = statement;
        if (!this.areBracketsMatch()) {
            return null;
        }
        if (!this.isValidSymbols()) {
            return null;
        }

        try {
            Parser p = new Parser(statement);
            Operator op = p.parse();
            Evaluator ev = new Evaluator(op);
            return ev.evaluate();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean areBracketsMatch() {
        int openBracketCounter = 0;
        for (char ch: this.statement.toCharArray()) {
            if (ch == ')' && openBracketCounter == 0) {
                return false;
            } else if (ch == '(') {
                openBracketCounter++;
            } else if (ch == ')') {
                openBracketCounter--;
            }
        }

        return openBracketCounter == 0;
    }

    private boolean isValidSymbols() {
        for (char ch: this.statement.toCharArray()) {
            if (!isValidSymbol(ch)) return false;
        }
        return true;
    }

    private boolean isValidSymbol(char symbol) {
        HashSet<Character> validBr = new HashSet<>(Arrays.asList('(', ')'));
        HashSet<Character> validOp = new HashSet<>(Arrays.asList('+', '/', '-', '*'));

        return Character.isDigit(symbol)
                || symbol == '.'
                || validOp.contains(symbol)
                || validBr.contains(symbol);
    }
}
