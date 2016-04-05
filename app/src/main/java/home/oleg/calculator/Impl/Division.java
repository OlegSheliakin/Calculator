package home.oleg.calculator.Impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

import home.oleg.calculator.Interfaces.IOperation;

/**
 * Created by Oleg on 05.04.2016.
 */
public class Division implements IOperation {
    @Override
    public char getName() {
        return '/';
    }

    @Override
    public double evaluate(Stack<Double> operands) throws IllegalArgumentException{
        BigDecimal secondOperand = new BigDecimal(operands.pop());
        BigDecimal firstOperand = new BigDecimal(operands.pop());
        return firstOperand.divide(secondOperand, MathContext.DECIMAL32).doubleValue();
    }

    @Override
    public int getPriority() {
        return 2;
    }

}
