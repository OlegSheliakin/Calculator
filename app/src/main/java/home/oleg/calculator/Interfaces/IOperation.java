package home.oleg.calculator.Interfaces;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Created by Oleg on 05.04.2016.
 */
public interface IOperation {
    char getName();
    double evaluate(Stack<Double> operands) ;
    int getPriority();
}
