package home.oleg.calculator.Impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

import home.oleg.calculator.Interfaces.IOperation;

/**
 * Created by Oleg on 05.04.2016.
 */
public class Multiplication implements IOperation {
    @Override
    public char getName() {
        return '*';
    }

    @Override
    public double evaluate(Stack<Double> operands)  {
        BigDecimal secondOperand = new BigDecimal(operands.pop());
        BigDecimal firstOperand = new BigDecimal(operands.pop());
        return firstOperand.multiply(secondOperand, MathContext.DECIMAL32).doubleValue();
    }

    @Override
    public int getPriority() {
        return 2;
    }

}
