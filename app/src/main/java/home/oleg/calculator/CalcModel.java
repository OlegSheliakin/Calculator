package home.oleg.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;

/**
 * Created by Oleg on 11.02.2016.
 */
public class CalcModel {

    public static final char ADD = '+';
    public static final char SUBTRACT = '-';
    public static final char DIVIDE = '/';
    public static final char MULTIPLY = '*';
    public static final char POINT = '.';

    private CalcModel (){}

    public static boolean isOperator(char operation) {
        return operation == ADD
                || operation == SUBTRACT
                || operation == MULTIPLY
                || operation == DIVIDE;
    }
    //gets priority of operation
    private static int priority(char operation) {
        if (operation == ADD || operation == SUBTRACT) {
            return 1;
        }
        if (operation == DIVIDE || operation == MULTIPLY) {
            return 2;
        }
        else return -1;
    }

    //evaluates the expression
    public static double evaluate(String expression) throws Exception {
        LinkedList<Double> operands = new LinkedList<>();
        LinkedList<Character> operations = new LinkedList<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (isOperator(c)) {
                while (!operations.isEmpty() && !isOperator(expression.charAt(i - 1)) && (expression.charAt(i) != POINT) &&
                        (priority(operations.getLast()) >= priority(c))) {

                    count(operands, operations.removeLast());
                }

                operations.add(c);
            } else {
                String op = "";
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == POINT)) {
                    op = op + expression.charAt(i++);
                }
                i--;
                operands.add(Double.parseDouble(op));
            }
            // adds '-1.0' to expression instead of '-'
            if (operations.size() > operands.size() && !operations.isEmpty()) {
                char op = operations.removeLast();
                if (op == SUBTRACT) {
                    operands.add(-1.0);
                    operations.add(MULTIPLY);
                }
            }
        }
        //sequentially counts each operand
        while (!operations.isEmpty()) {
            count(operands, operations.removeLast());
        }

        return operands.getFirst();
    }
    //counts the value of two operands
    private static void count(LinkedList<Double> operands, char operation) throws Exception {

        BigDecimal firstOperand = new BigDecimal(operands.removeLast());
        BigDecimal secondOperand = new BigDecimal(operands.removeLast());

        switch (operation) {
            case ADD:
                operands.add(secondOperand.add(firstOperand).doubleValue());
                break;
            case SUBTRACT:
                operands.add(secondOperand.subtract(firstOperand).doubleValue());
                break;
            case DIVIDE:
                if (firstOperand.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException();// почему?
                } else {
                    operands.add(secondOperand.divide(firstOperand, MathContext.DECIMAL64).doubleValue());
                }
                break;
            case MULTIPLY:
                operands.add(secondOperand.multiply(firstOperand, MathContext.DECIMAL64).doubleValue());
                break;

        }
    }
}
