package home.oleg.calculator;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import home.oleg.calculator.Impl.Addition;
import home.oleg.calculator.Impl.Division;
import home.oleg.calculator.Impl.Multiplication;
import home.oleg.calculator.Impl.Substraction;
import home.oleg.calculator.Interfaces.IOperation;

/**
 * Created by Oleg on 11.02.2016.
 */
public class Calculator {

    private Stack<Double> operandsStack;
    private Stack<IOperation> operationsStack;
    private Map<Character, IOperation> operationsMap;

    public Calculator(){
        operationsMap = new HashMap<>();
        operationsMap.put('+', new Addition());
        operationsMap.put('-', new Substraction());
        operationsMap.put('*', new Multiplication());
        operationsMap.put('/', new Division());
    }

    //evaluates the expression
    public double calculate(String expression) throws Exception {
        operandsStack = new Stack<>();
        operationsStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (MathsOperations.isOperator(c)) {
                while (!operationsStack.isEmpty() && !MathsOperations.isOperator(expression.charAt(i - 1)) && (expression.charAt(i) != '.') &&
                        (operationsStack.peek().getPriority() >= operationsMap.get(c).getPriority())) {

                   // evaluate(operands, operations.pop());
                    operandsStack.add(operationsStack.pop().evaluate(operandsStack));
                }

                operationsStack.add(operationsMap.get(c));
            } else {
                String op = "";
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    op = op + expression.charAt(i++);
                }
                i--;
                operandsStack.add(Double.parseDouble(op));
            }
            // adds '-1.0' to expression instead of '-'
            if (operationsStack.size() > operandsStack.size() && !operationsStack.isEmpty()) {
                char op = operationsStack.pop().getName();
                if (op == MathsOperations.SUBTRACT) {
                    operandsStack.add(-1.0);
                    operationsStack.add(new Multiplication());
                }
            }
        }
        //sequentially counts each operand
        while (!operationsStack.isEmpty()) {
            operandsStack.add(operationsStack.pop().evaluate(operandsStack));
        }

        return operandsStack.firstElement();
    }


}
