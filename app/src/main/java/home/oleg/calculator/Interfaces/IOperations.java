package home.oleg.calculator.Interfaces;

import java.math.BigDecimal;

/**
 * Created by Oleg on 05.04.2016.
 */
public interface IOperations {
    String getName();
    double evaluate(BigDecimal ...args);
    int getPiority ();
}
