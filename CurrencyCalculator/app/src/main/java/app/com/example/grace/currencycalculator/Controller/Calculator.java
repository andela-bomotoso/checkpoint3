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
    Expression subExpression;
    ExpressionAnalyzer expressionAnalyzer = new ExpressionAnalyzer();

    public double compute(String expressionString) {

        Expression expression = expressionAnalyzer.breakDownExpression(expressionString);
        List < ExpressionPart > expressionParts = expression.getExpressionParts();

        computedValue = Double.parseDouble(expressionParts.get(0).getValue());
        for(int i = 1; i < expressionParts.size(); i++) {

            ExpressionPart currentExpressionPart = expressionParts.get(i);
            ExpressionPart previousExpressionPart = expressionParts.get(i-1);

            if(currentExpressionPart.isOperand()) {

                currentOperandString = currentExpressionPart.getValue();
                currentOperator = previousExpressionPart.getValue();

                if ((currentOperandString).startsWith("(")) {
                    analyzeExpressionInParenthesis(currentExpressionPart,previousExpressionPart,expressionParts,i);
                }
                else {
                    currentOperand = Double.parseDouble(currentOperandString);
                }
                computedValue = getTemporaryValue();
            }
        }

        return computedValue;
    }

    private void analyzeExpressionInParenthesis(ExpressionPart currentExpressionPart,ExpressionPart previousExpressionPart,List<ExpressionPart> expressionParts,int i) {

        currentOperandString = currentOperandString.substring(1, currentOperandString.length() - 1);
        currentExpressionPart.setValue(currentOperandString);

        if (previousExpressionPart.isOperand()) {
            currentOperator = "*";
        }
        double computedValueBuffer = computedValue;
        String currentOperatorBuffer = currentOperator;
        recomputeSubexpression(i, expressionParts, computedValueBuffer, currentOperatorBuffer);
    }

    private void recomputeSubexpression(int currentIndex, List<ExpressionPart> expressionParts,double computedValueBuffer, String currentOperatorBuffer) {

        subExpression = expressionAnalyzer.breakDownExpression(currentOperandString);
        currentOperand = compute(expressionAnalyzer.generateExpressionString(subExpression));
        expressionParts.set(currentIndex,new Operand(currentOperand+""));
        computedValue = computedValueBuffer;
        currentOperator = currentOperatorBuffer;
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

        return computedValue;
    }

    private double adjustComputedValue(double expressionValue) {

        if (previousOperator.equals("+")) {
            computedValue = (computedValue - previousOperand) + expressionValue;

        } else if (previousOperator.equals("-")) {
            computedValue = (computedValue + previousOperand) - expressionValue;
        }
        return computedValue;
    }
}
