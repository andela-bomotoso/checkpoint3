package app.com.example.grace.currencycalculator;


import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;

public class Calculator {

    double previousOperand = 0.0;
    String previousOperator = "";
    String currentOperator = "";
    String currentOperandString = "";
    double currentOperand = 0.0;
    double computedValue = 0.0;
    ExpressionPart currentExpressionPart;
    ExpressionPart previousExpressionPart;
    ExpressionPart firstNumber;


    public double compute(Expression expression) {

        List<ExpressionPart> expressionParts = expression.getExpressionParts();
        firstNumber = expressionParts.get(0);
        computedValue = Double.parseDouble(firstNumber.getValue());

        for(int i = 1; i < expressionParts.size(); i++) {

            ExpressionPart currentExpressionPart = expressionParts.get(i);
            ExpressionPart previousExpressionPart = expressionParts.get(i-1);

            if(currentExpressionPart.isOperand()) {

                currentOperandString = currentExpressionPart.getValue();
                currentOperator = previousExpressionPart.getValue();

                if((currentOperandString).startsWith("(") ){
                    currentOperandString  =  currentOperandString.substring(1, currentOperandString.length() - 1);

                    if(previousExpressionPart.isOperand()){
                        currentOperator = "*";
                    }
                }
                currentOperand = Double.parseDouble(currentOperandString);
                computedValue = getTemporaryValue();
            }
        }

        return computedValue;
    }

    private double getTemporaryValue() {

        switch (currentOperator) {

            case "+":
                computedValue = computedValue + currentOperand;
                break;

            case "-":
                computedValue = computedValue - currentOperand;

            case "*":
            case "/":
              computedValue = evaluatePrecedence();
                break;
        }

        previousOperand = currentOperand;
        previousOperator = currentOperator;

        return computedValue;
    }

    private double evaluatePrecedence() {
        double expressionValue;
        switch (currentOperator) {

            case "*":
                if(!previousOperator.isEmpty()){
                        expressionValue = previousOperand * currentOperand;
                        computedValue =  analyzePrecedence(expressionValue);
                } else {
                computedValue = computedValue * currentOperand;
            }
                break;

            case "/":
                if(!previousOperator.isEmpty()){
                    expressionValue = previousOperand / currentOperand;
                    computedValue = analyzePrecedence(expressionValue);
                }
                else {
                    computedValue = computedValue / currentOperand;
                }
                break;
        }

        return computedValue;
    }

    private double analyzePrecedence(double expressionValue) {

        if (previousOperator.equals("+")) {
            computedValue = (computedValue - previousOperand) + expressionValue;
        } else if (previousOperator.equals("-")) {
            computedValue = (computedValue + previousOperand) - expressionValue;
        }
        return computedValue;
    }

    public boolean isValid(Expression expression) {

        boolean isValid = true;
        List<ExpressionPart> expressionParts = expression.getExpressionParts();
        firstNumber = expressionParts.get(0);
        String currentOperandString="";

        for(int i = 1; i < expressionParts.size(); i++) {
            ExpressionPart currentExpressionPart = expressionParts.get(i);
            ExpressionPart previousExpressionPart = expressionParts.get(i - 1);

            if(currentExpressionPart.isOperand()) {
                currentOperandString = currentExpressionPart.getValue();
                currentOperator = previousExpressionPart.getValue();
            }

            if (startWithOperator(firstNumber) || DoubleOperator(previousExpressionPart, currentExpressionPart) || bracketMismatch(currentOperandString)
                    || divisionByZero(currentOperandString) || doubleDots(currentOperandString))
            {
                isValid = false;
                break;
            }
        }
            return isValid;
    }
    private boolean startWithOperator(ExpressionPart firstNumber) {

        return firstNumber.isOperator();
    }

    private boolean DoubleOperator(ExpressionPart previousExpressionPart, ExpressionPart currentExpressionPart) {
        return (currentExpressionPart.isOperator() && previousExpressionPart.isOperator());
    }

    private boolean bracketMismatch(String currentOperandString) {
        return (currentOperandString.startsWith("(") && !currentOperandString.endsWith(")"));
    }

    private boolean divisionByZero(String currentOperandString) {
        return (currentOperator.equals("/") && currentOperandString.equals("0"));
    }

    private boolean doubleDots(String currentOperandString) {
        return ( (currentOperandString.length() - currentOperandString.replace(".", "").length()) > 1);
    }
}
