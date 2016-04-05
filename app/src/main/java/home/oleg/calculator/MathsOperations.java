package home.oleg.calculator;

import home.oleg.calculator.Impl.Substraction;
import home.oleg.calculator.Interfaces.IOperation;

/**
 * Created by Oleg on 04.04.2016.
 */
public class MathsOperations {

    public static final char ADD = '+';
    public static final char SUBTRACT = '-';
    public static final char DIVIDE = '/';
    public static final char MULTIPLY = '*';

    private static final int LOW_PRIORITY = 1;
    private static final int HIGH_PRIORITY = 2;


    private MathsOperations(){}

    public static IOperation addOperator (char operation){
        switch (operation){
            case SUBTRACT:
                return new Substraction();
            default:
                return null;

        }
    }

    public static boolean isOperator(char operation) {
        return operation == ADD
                || operation == SUBTRACT
                || operation == DIVIDE
                || operation == MULTIPLY;
    }
}
