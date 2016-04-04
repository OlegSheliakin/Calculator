package home.oleg.calculator;

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

    //returns priority of operation
    public static int priority(char operation) {
        if (operation == ADD || operation == SUBTRACT) {
            return LOW_PRIORITY;
        }
        if (operation == DIVIDE || operation == MULTIPLY) {
            return HIGH_PRIORITY;
        }
        else return -1;
    }

    public static boolean isOperator(char operation) {
        return operation == ADD
                || operation == SUBTRACT
                || operation == DIVIDE
                || operation == MULTIPLY;
    }
}
