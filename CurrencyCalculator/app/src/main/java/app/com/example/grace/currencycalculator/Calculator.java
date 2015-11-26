package app.com.example.grace.currencycalculator;

import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.SubExpression;

public class Calculator {

    public double computeExpression(Expression expression) {

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

        return evaluateExpressionParts(expressionOperators,expressionOperands);
    }

    public double evaluateExpressionParts(List<ExpressionPart> expressionOperators, List<ExpressionPart> expressionOperands) {
        double expressionResult = Double.parseDouble(expressionOperands.get(0).getValue());

        for(int i = 0; i < expressionOperators.size(); i++) {
            double currentOperand = 0;

            String currentOperandString = expressionOperands.get(i + 1).getValue();

            if(currentOperandString.startsWith("(")) {

                String subExpressionString =  currentOperandString.substring(1,currentOperandString.length()-1);
                SubExpression subExpression =  new SubExpression();
                subExpression.setValue(subExpressionString);
                currentOperand = computeExpression(subExpression);
            }
            else {
                currentOperand = Double.parseDouble(currentOperandString);
            }
            String currentOperator = expressionOperators.get(i).getValue();
            expressionResult = evaluateTempResult(expressionResult,currentOperand,currentOperator);
        }

        return expressionResult;
    }

    public double evaluateTempResult(double tempResult, double currentOperand, String currentOperator) {

        switch (currentOperator) {

            case "+":
                tempResult = tempResult + currentOperand;
                break;

            case "-":
                tempResult = tempResult - currentOperand;
                break;

            case "*":
                tempResult = tempResult * currentOperand;
                break;

            case "/":
                tempResult = tempResult / currentOperand;
        }
        return tempResult;
    }

    public boolean isValid(Expression expression) {
        return true;
    }

}
