package app.com.example.grace.currencycalculator.Controller.Calculator1;

import java.util.HashMap;

public enum  CalculatorOperator {

    ADD {double evaluate(double a, double b) {return  a+b;}},
    SUBTRACT {double evaluate(double a, double b) {return a-b;}},
    MULTIPLY {double evaluate(double a, double b) {return a*b;}},
    DIVIDE {double evaluate (double a, double b) {return a/b;}};

    static final HashMap<String,CalculatorOperator> operators;

    static {
        operators = new HashMap<String,CalculatorOperator>();
        operators.put(CalculatorTokenType.ADDITION,ADD);
        operators.put(CalculatorTokenType.SUBTRACTION,SUBTRACT);
        operators.put(CalculatorTokenType.MULTIPLICATION,MULTIPLY);
        operators.put(CalculatorTokenType.DIVISION,DIVIDE);
    }

    public static final CalculatorOperator getOperator(Object token) {
        return (CalculatorOperator)operators.get(token);
    }
    abstract double evaluate(double a, double b);

}
