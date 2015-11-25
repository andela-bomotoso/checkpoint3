package app.com.example.grace.currencycalculator;

import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;

public class Calculator {

    private Operand operand;
    private Operand operator;
    private Expression expression;
    private double expressionResult;

    public Operand getOperand() {
        return operand;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
    }

    public Operand getOperator() {
        return operator;
    }

    public void setOperator(Operand operator) {
        this.operator = operator;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public double getExpressionResult() {
        return expressionResult;
    }

    public void setExpressionResult(double expressionResult) {
        this.expressionResult = expressionResult;
    }

    public double computeExpression(Expression expression) {

        double expressionResult;
        List<ExpressionPart> currentExpression = expression.getExpressionParts();
        int size = currentExpression.size();

        if(currentExpression.get(size-1).isOperand()) {
            expressionResult =  Double.parseDouble(currentExpression.toString());
            setExpressionResult(expressionResult);
        }

        return getExpressionResult();
    }

    

    public boolean isValid(Expression expression) {
        return true;
    }


}
