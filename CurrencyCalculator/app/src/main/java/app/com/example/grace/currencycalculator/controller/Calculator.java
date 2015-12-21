package app.com.example.grace.currencycalculator.controller;


import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;

public class Calculator {

    private Validator expressionValidator;
    private ExpressionAnalyzer expressionAnalyzer;
    private String previousOperator;
    private String currentOperator;
    private String currentOperandString;
    private String firstExpressionPart;
    private double previousOperand;
    private double currentOperand ;
    private double computedValue;
    private double previousExpressionValue;
    private double previousExpValue;
    private double nonPrecedenceComputedValue;
    private boolean nonPrecedence;
    double operandBeforePrecedence;

    public double compute(Expression expression) {

        initializeVariables();
        expressionValidator = new Validator();
        expressionAnalyzer = new ExpressionAnalyzer();

        List < ExpressionPart > expressionParts = expression.getExpressionParts();
        firstExpressionPart = expressionParts.get(0).getValue();

        analyzeFirstExpressionPart(expressionParts);

        for(int index = 1; index < expressionParts.size(); index++) {
            analyzeCurrentExpressionPart(expressionParts,index);
        }
        
        clear();
        return computedValue;
    }

    private void analyzeFirstExpressionPart(List<ExpressionPart> expressionParts) {

        if(firstExpressionPart.contains("(")) {
            currentOperandString = expressionValidator.removeBrackets(firstExpressionPart);
            computedValue = 0.0;
            analyzeExpressionInParenthesis(expressionParts,0);
        }
        else
            computedValue = Double.parseDouble(expressionParts.get(0).getValue());
    }

    private void analyzeCurrentExpressionPart(List<ExpressionPart>expressionParts,int index) {

        ExpressionPart currentExpressionPart = expressionParts.get(index);
        ExpressionPart previousExpressionPart = expressionParts.get(index-1);

        if(currentExpressionPart.isOperand()) {

            currentOperandString = currentExpressionPart.getValue();
            currentOperator = previousExpressionPart.getValue();

            if ((currentOperandString).startsWith("(")) {
                analyzeOpenParenthesis(expressionParts,currentExpressionPart,index);
            }
            else {
                currentOperand = Double.parseDouble(currentOperandString);
            }
            computedValue = getTemporaryValue();
        }
    }

    private void analyzeOpenParenthesis(List<ExpressionPart>expressionParts,ExpressionPart currentExpressionPart, int index) {

        currentOperandString = expressionValidator.removeBrackets(currentOperandString);
        currentExpressionPart.setValue(currentOperandString);
        analyzeExpressionInParenthesis(expressionParts,index);
    }

    private void analyzeExpressionInParenthesis(List<ExpressionPart> expressionParts,int currentIndex) {

        double computedValueBuffer = computedValue;
        String previousOperatorBuffer = previousOperator;
        String currentOperatorBuffer = currentOperator;
        double previousOperandBuffer = previousOperand;
        Expression expression = expressionAnalyzer.breakDownExpression(currentOperandString);
        currentOperand = compute(expression);
        expressionParts.set(currentIndex, new Operand(currentOperand + ""));

        if(computedValueBuffer != 0) {
            computedValue = computedValueBuffer;
        }
        currentOperator = currentOperatorBuffer;
        previousOperator = previousOperatorBuffer;
        previousOperand = previousOperandBuffer;

    }

    private double getTemporaryValue() {

        switch (currentOperator) {
            case "+":
                analyzeAddition();
                break;
            case "-":
                analyzeSubtraction();
                break;
            case "*":
            case "/":
              computedValue = evaluatePrecedence();
                break;
        }
        resetPrevious();
        return computedValue;
    }

    private void analyzeAddition() {

        operandBeforePrecedence = computedValue;
        computedValue = computedValue + currentOperand;
        previousExpValue = 1;
        updatePrecedence();
    }

    private void analyzeSubtraction() {

        operandBeforePrecedence = computedValue;
        computedValue = computedValue - currentOperand;
        previousExpValue = -1;
        updatePrecedence();
    }

    private double evaluatePrecedence() {

        double expressionValue;
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
        resetPrevious();
        return computedValue;
    }

    private double adjustComputedValue(double expressionValue) {

        if (previousOperator.equals("+")) {
            computedValue = (computedValue - previousOperand) + expressionValue;

        } else if (previousOperator.equals("-")) {
            computedValue = (computedValue + previousOperand) - expressionValue;
        }
        else {
            switch (currentOperator) {
                case "*":
                   getAdjustedMultiplication();
                    break;
                case "/":
                    getAdjustedDivision();
                    break;
            }
        }
        previousExpressionValue = previousExpValue * expressionValue;
        nonPrecedence = false;
        return computedValue;
    }

    private void updatePrecedence() {
        nonPrecedenceComputedValue = computedValue;
        nonPrecedence = true;
    }

    private void resetPrevious() {
        previousOperand = currentOperand;
        previousOperator = currentOperator;
    }

    private double getAdjustedDivision() {

        if(operandBeforePrecedence != 0) {
            computedValue = previousExpressionValue / currentOperand + operandBeforePrecedence;
        } else
            computedValue = computedValue / currentOperand;
        return computedValue;
    }

    private double getAdjustedMultiplication() {

        if(operandBeforePrecedence != 0) {
            computedValue = previousExpressionValue * currentOperand + operandBeforePrecedence;
        } else
            computedValue = computedValue * currentOperand;
        return computedValue;
    }

    private void clear() {
        previousOperand = 0.0;
        previousOperator = "";
        currentOperator = "";
        currentOperandString = "";
        currentOperand = 0.0;
        previousExpressionValue = 0.0;
        previousExpValue = 1;
        nonPrecedence = false;
        previousExpressionValue = 0;
        previousExpValue = 0;
        firstExpressionPart = "";
        expressionAnalyzer = new ExpressionAnalyzer();
        operandBeforePrecedence = 0;
    }

    private void initializeVariables() {
        previousOperator = "";
        currentOperator = "";
        currentOperandString = "";
        firstExpressionPart="";
        previousOperand = 0.0;
        currentOperand = 0.0;
        computedValue = 0.0;
        previousExpressionValue = 1;
        previousExpValue = 0;
        nonPrecedenceComputedValue = 0;
        nonPrecedence = false;
        operandBeforePrecedence = 0;
    }

}
