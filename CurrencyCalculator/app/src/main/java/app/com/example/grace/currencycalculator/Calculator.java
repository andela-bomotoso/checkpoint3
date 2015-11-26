package app.com.example.grace.currencycalculator;

import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.SubExpression;

public class Calculator {

    String previousOperator = "";
    double previousOperand = 0;

    public double compute(Expression expression) {

        List<ExpressionPart> expressionOperators =  new ArrayList<ExpressionPart>();
        List<ExpressionPart> expressionOperands =  new ArrayList<ExpressionPart>();

        for(ExpressionPart expressionPart:expression.getExpressionParts()) {

            if(expressionPart.isOperator()) {
                expressionOperators.add(expressionPart);
            }
            else {
                expressionOperands.add(expressionPart);
            }
        }

        double computedValue = evaluate(expressionOperators, expressionOperands);
        return computedValue;
    }

    public double evaluate(List<ExpressionPart> expressionOperators, List<ExpressionPart> expressionOperands) {

        double expressionResult = Double.parseDouble(expressionOperands.get(0).getValue());

        for(int i = 0; i < expressionOperators.size(); i++) {
            double currentOperand = 0;

            String currentOperandString = expressionOperands.get(i + 1).getValue();

//            if(currentOperandString.startsWith("(")) {
//
//                String subExpressionString =  currentOperandString.substring(1,currentOperandString.length()-1);
//                SubExpression subExpression =  new SubExpression();
//                subExpression.setValue(subExpressionString);
//
//                currentOperand = compute(subExpression);
//            }
                currentOperand = Double.parseDouble(currentOperandString);


            String currentOperator = expressionOperators.get(i).getValue();
            expressionResult = getTemporaryValue(expressionResult, currentOperand, currentOperator);
        }

        return expressionResult;
    }

    public double getTemporaryValue(double tempResult, double currentOperand, String currentOperator) {

        switch (currentOperator) {

            case "+":
                tempResult = tempResult + currentOperand;
                break;

            case "-":
                tempResult = tempResult - currentOperand;
                break;

            case "*":
                if(previousOperator.equals("+") && ! previousOperator.isEmpty()) {
                    tempResult = (tempResult - previousOperand)  + (previousOperand * currentOperand);
                }
                else if (previousOperator.equals("-") && ! previousOperator.isEmpty()) {
                    tempResult = (tempResult + previousOperand)  - (previousOperand * currentOperand);
                }

                else {
                    tempResult = tempResult * currentOperand;
                    break;
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

        }

        previousOperand = currentOperand;
        previousOperator = currentOperator;

        return tempResult;

    }

    public boolean isValid(Expression expression) {
        return true;
    }

}
