package home.oleg.calculator;

import android.app.Activity;
import android.app.Notification;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;

/**
 * Created by Oleg on 11.02.2016.
 */
public class Calculator {

    private Calculator(){}

    //evaluates the expression
    public static double calculate(String expression) throws Exception {
        LinkedList<Double> operands = new LinkedList<>();
        LinkedList<Character> operations = new LinkedList<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (MathsOperations.isOperator(c)) {
                while (!operations.isEmpty() && !MathsOperations.isOperator(expression.charAt(i - 1)) && (expression.charAt(i) != '.') &&
                        (MathsOperations.priority(operations.getLast()) >= MathsOperations.priority(c))) {

                    evaluate(operands, operations.removeLast());
                }

                operations.add(c);
            } else {
                String op = "";
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    op = op + expression.charAt(i++);
                }
                i--;
                operands.add(Double.parseDouble(op));
            }
            // adds '-1.0' to expression instead of '-'
            if (operations.size() > operands.size() && !operations.isEmpty()) {
                char op = operations.removeLast();
                if (op == MathsOperations.SUBTRACT) {
                    operands.add(-1.0);
                    operations.add(MathsOperations.MULTIPLY);
                }
            }
        }
        //sequentially counts each operand
        while (!operations.isEmpty()) {
            evaluate(operands, operations.removeLast());
        }

        return operands.getFirst();
    }
    //counts the value of two operands
    private static void evaluate(LinkedList<Double> operands, char operation) throws Exception {

        BigDecimal firstOperand = new BigDecimal(operands.removeLast());
        BigDecimal secondOperand = new BigDecimal(operands.removeLast());

        switch (operation) {
            case MathsOperations.ADD:
                operands.add(secondOperand.add(firstOperand).doubleValue());
                break;
            case MathsOperations.SUBTRACT:
                operands.add(secondOperand.subtract(firstOperand).doubleValue());
                break;
            case MathsOperations.DIVIDE:
                if (firstOperand.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException();
                } else {
                    operands.add(secondOperand.divide(firstOperand, MathContext.DECIMAL64).doubleValue());
                }
                break;
            case MathsOperations.MULTIPLY:
                operands.add(secondOperand.multiply(firstOperand, MathContext.DECIMAL64).doubleValue());
                break;

        }
    }
}
