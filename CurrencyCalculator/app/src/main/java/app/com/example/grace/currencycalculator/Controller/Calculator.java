package app.com.example.grace.currencycalculator.Controller;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;

public class Calculator {

    double previousOperand = 0.0;
    String previousOperator = "";
    String currentOperator = "";
    String currentOperandString = "";
    double currentOperand = 0.0;
    double computedValue = 0.0;
    double previousExpressionValue = 0;
    double previousExpValue = 0;
    boolean nonPrecedence = false;
    ExpressionAnalyzer expressionAnalyzer;

    public double compute(String expressionString) {

        expressionAnalyzer = new ExpressionAnalyzer();
        Expression expression = expressionAnalyzer.breakDownExpression(expressionString);
        List < ExpressionPart > expressionParts = expression.getExpressionParts();

        computedValue = Double.parseDouble(expressionParts.get(0).getValue());

        for(int index = 1; index < expressionParts.size(); index++) {

            ExpressionPart currentExpressionPart = expressionParts.get(index);
            ExpressionPart previousExpressionPart = expressionParts.get(index-1);

            if(currentExpressionPart.isOperand()) {

                currentOperandString = currentExpressionPart.getValue();
                currentOperator = previousExpressionPart.getValue();

                if ((currentOperandString).startsWith("(")) {
                    currentOperandString = currentOperandString.substring(1, currentOperandString.length() - 1);
                    currentExpressionPart.setValue(currentOperandString);
                    analyzeExpressionInParenthesis(expressionParts,index);
                }
                else {
                    currentOperand = Double.parseDouble(currentOperandString);
                }
                computedValue = getTemporaryValue();
            }
        }

        clear();

        return computedValue;
    }

    private void analyzeExpressionInParenthesis(List<ExpressionPart> expressionParts,int currentIndex) {

        double computedValueBuffer = computedValue;
        String currentOperatorBuffer = currentOperator;
        currentOperand = compute(currentOperandString);
        expressionParts.set(currentIndex,new Operand(currentOperand+""));
        computedValue = computedValueBuffer;
        currentOperator = currentOperatorBuffer;
    }

    private double getTemporaryValue() {
        switch (currentOperator) {

            case "+":
                computedValue = computedValue + currentOperand;
                previousExpValue = 1;
                nonPrecedence = true;
                break;

            case "-":
                computedValue = computedValue - currentOperand;
                previousExpValue = -1;
                nonPrecedence = true;
                break;

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

        double expressionValue = 0;

        switch (currentOperator) {

            case "*":
                if(!previousOperator.isEmpty() ){
                        expressionValue = previousOperand * currentOperand;
                        computedValue =  adjustComputedValue(expressionValue);
                } else {
                computedValue = computedValue * currentOperand;
            }
                break;

            case "/":
                if(!previousOperator.isEmpty()){
                    expressionValue = previousOperand / currentOperand;
                    computedValue = adjustComputedValue(expressionValue);
                }
                else {
                    computedValue = computedValue / currentOperand;
                }
                break;
        }
        previousOperand = currentOperand;
        previousOperator = currentOperator;


        return computedValue;
    }

    private double adjustComputedValue(double expressionValue) {
        //previousExpressionValue = previousExpValue * previousExpressionValue;

        if (previousOperator.equals("+")) {
            computedValue = (computedValue - previousOperand) + expressionValue;

        } else if (previousOperator.equals("-")) {
            computedValue = (computedValue + previousOperand) - expressionValue;
        }
        else if (previousOperator.equals("*")) {

            switch (currentOperator) {
                case "*":
                    computedValue = computedValue * currentOperand;
                    break;
                case "/":
                    if(!nonPrecedence) {
                        computedValue = computedValue / currentOperand;
                    }
                    else {
                        computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue/currentOperand);
                    }

                    break;
            }

        }
        else if (previousOperator.equals("/")) {
            switch (currentOperator) {
                case "*":
                    if(!nonPrecedence) {
                        computedValue = computedValue * currentOperand;
                    }
                    else {
                        computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue * currentOperand);
                    }
                    break;

                case "/":
                    computedValue = computedValue / currentOperand;
                    break;
            }
        }
        previousExpressionValue = previousExpValue * expressionValue;
        return computedValue;
    }

    public void clear() {
        previousOperand = 0.0;
        previousOperator = "";
        currentOperator = "";
        currentOperandString = "";
        currentOperand = 0.0;
        previousExpressionValue = 0.0;
        previousExpValue = 0.0;
        nonPrecedence = false;
    }

}
