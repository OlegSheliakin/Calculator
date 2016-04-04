package home.oleg.calculator.Impl;

import java.math.BigDecimal;
import java.math.MathContext;

import home.oleg.calculator.Interfaces.IOperations;

/**
 * Created by Oleg on 05.04.2016.
 */
public class Division implements IOperations{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public double evaluate(BigDecimal... args) {
        return args[0].divide(args[1], MathContext.DECIMAL32).doubleValue();
    }

    @Override
    public int getPiority() {
        return 2;
    }
}
