package app.com.example.grace.currencycalculator;


import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;

public class Calculator1 {

    double previousOperand = 0.0;
    String previousOperator = "";
    String currentOperator = "";
    double currentOperand = 0.0;

    public double compute(Expression expression) {

        List<ExpressionPart> expressionParts = expression.getExpressionParts();
        ExpressionPart firstNumber = expressionParts.get(0);
        double computedValue = Double.parseDouble(firstNumber.getValue());

        for(int i = 1; i < expressionParts.size(); i++) {

            String currentOperandString = expressionParts.get(i).getValue();

            if(expressionParts.get(i).isOperand()) {

                if((currentOperandString).startsWith("(") && expressionParts.get(i-1).isOperand()) {
                    currentOperandString  =  currentOperandString.substring(1,currentOperandString.length()-1);
                    currentOperand = Double.parseDouble(currentOperandString);
                    currentOperator = "*";
                    computedValue = getTemporaryValue(computedValue,currentOperand,currentOperator);
                }
                else if ((currentOperandString).startsWith("(") && expressionParts.get(i-1).isOperator()) {
                    currentOperandString  =  currentOperandString.substring(1,currentOperandString.length()-1);
                   currentOperand = Double.parseDouble(currentOperandString);

                    currentOperator = expressionParts.get((i - 1)).getValue();
                    computedValue = getTemporaryValue(computedValue,currentOperand,currentOperator);
                }
                else {
                    currentOperator = expressionParts.get((i - 1)).getValue();
                    currentOperand = Double.parseDouble(currentOperandString);
                    computedValue = getTemporaryValue(computedValue,currentOperand,currentOperator);
                }
            }
        }

        return computedValue;
    }

    public double getTemporaryValue(double tempResult, double currentOperand, String currentOperator) {

        switch (currentOperator) {

            case "+":
                tempResult = tempResult + currentOperand;
                break;

            case "-":
                tempResult = tempResult - currentOperand;

            case "*":
            case "/":
              tempResult = checkPrecedence(tempResult,currentOperand,currentOperator);
                break;
        }

        previousOperand = currentOperand;
        previousOperator = currentOperator;

        return tempResult;
    }

    public double checkPrecedence(double tempResult, double currentOperand, String currentOperator) {

        switch (currentOperator) {

            case "*":
            if (previousOperator.equals("+") && !previousOperator.isEmpty()) {
                tempResult = (tempResult - previousOperand) + (previousOperand * currentOperand);
            } else if (previousOperator.equals("-") && !previousOperator.isEmpty()) {
                tempResult = (tempResult + previousOperand) - (previousOperand * currentOperand);
            } else {
                tempResult = tempResult * currentOperand;
            }
                break;

            case "/":
                if(previousOperator.equals("+") && ! previousOperator.isEmpty()) {
                    tempResult = (tempResult - previousOperand)  + (previousOperand / currentOperand);
                }
                else if (previousOperator.equals("-") && ! previousOperator.isEmpty()) {
                    tempResult = (tempResult + previousOperand)  - (previousOperand / currentOperand);
                }

                else {
                    tempResult = tempResult / currentOperand;
                    break;
                }
                break;
        }

        return tempResult;
    }

}
