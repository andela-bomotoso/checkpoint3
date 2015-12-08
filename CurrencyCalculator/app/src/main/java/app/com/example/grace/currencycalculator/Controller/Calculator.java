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
    double nonPrecedenceComputedValue = 0;
    String firstExpressionPart="";
    ExpressionAnalyzer expressionAnalyzer;
    Validator expressionValidator;

    public double compute(String expressionString) {

        expressionAnalyzer = new ExpressionAnalyzer();
        expressionValidator = new Validator();
        Expression expression = expressionAnalyzer.breakDownExpression(expressionString);
        List < ExpressionPart > expressionParts = expression.getExpressionParts();
        firstExpressionPart = expressionParts.get(0).getValue();
        if(firstExpressionPart.contains("(")) {
           currentOperandString = expressionValidator.removeBrackets(firstExpressionPart);
            computedValue = 0.0;
            analyzeExpressionInParenthesis(expressionParts,0);
        }
        else
                computedValue = Double.parseDouble(expressionParts.get(0).getValue());

        for(int index = 1; index < expressionParts.size(); index++) {

            ExpressionPart currentExpressionPart = expressionParts.get(index);
            ExpressionPart previousExpressionPart = expressionParts.get(index-1);

            if(currentExpressionPart.isOperand()) {

                currentOperandString = currentExpressionPart.getValue();
                currentOperator = previousExpressionPart.getValue();

                if ((currentOperandString).startsWith("(")) {
                    currentOperandString = expressionValidator.removeBrackets(currentOperandString);
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
        if(computedValueBuffer!=0) {
            computedValue = computedValueBuffer;
        }
        currentOperator = currentOperatorBuffer;
    }

    private double getTemporaryValue() {
        switch (currentOperator) {

            case "+":
                computedValue = computedValue + currentOperand;
                previousExpValue = 1;
                nonPrecedenceComputedValue = computedValue;
                nonPrecedence = true;
                break;

            case "-":
                computedValue = computedValue - currentOperand;
                previousExpValue = -1;
                nonPrecedenceComputedValue = computedValue;
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
                    //computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue/currentOperand);
            }
                break;

            case "/":
                if(!previousOperator.isEmpty()){
                    expressionValue = previousOperand / currentOperand;
                    computedValue = adjustComputedValue(expressionValue);
                }
                else {
                    //computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue/currentOperand);
                    computedValue = computedValue / currentOperand;
                }
                break;
        }
        previousOperand = currentOperand;
        previousOperator = currentOperator;
        //previousExpressionValue = computedValue;

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
                    //computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue*currentOperand);
                    break;
                case "/":
                    if(!nonPrecedence) {
                        //computedValue = computedValue / currentOperand;
                        if(previousExpressionValue != 0) {
                            computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue / currentOperand);
                        }
                        else {
                            computedValue = computedValue / currentOperand;
                        }
                    }
                    else {
                        //computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue/currentOperand);
                        computedValue = computedValue / currentOperand;
                    }
                    //computedValue = computedValue/currentOperand;

                    break;
            }

        }
        else if (previousOperator.equals("/")) {
            switch (currentOperator) {
                case "*":
                    if(!nonPrecedence) {
                        //computedValue = computedValue * currentOperand;
                        if(previousExpressionValue != 0) {
                            computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue * currentOperand);
                        } else
                        {
                            computedValue = computedValue * currentOperand;
                        }
                    }
                    else {
                        //computedValue = (computedValue - previousExpressionValue) + (previousExpressionValue * currentOperand);
                        computedValue = computedValue * currentOperand;
                    }
                    break;
                case "/":
                    computedValue = computedValue / currentOperand;
                    break;
            }
        }
        previousExpressionValue = previousExpValue * expressionValue;
        nonPrecedence = false;
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
        previousExpressionValue = 0;
        previousExpValue = 0;
        firstExpressionPart = "";


    }

}
