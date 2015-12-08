package app.com.example.grace.currencycalculator.Controller.Calculator1;

import java.util.LinkedList;

public class Calculator1 {
    CalculatorParser calculatorParser;
    CalculatorEvaluator calculatorEvaluator;
    LinkedList<Object>result;

    public double calculate(String expression) {
            result = new LinkedList<>();
            calculatorParser = new CalculatorParser();
            calculatorEvaluator = new CalculatorEvaluator();
        try {
            result = calculatorParser.parse(expression);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return calculatorEvaluator.evaluate(result);
    }
}

