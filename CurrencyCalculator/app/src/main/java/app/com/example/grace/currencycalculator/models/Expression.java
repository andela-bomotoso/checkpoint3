package app.com.example.grace.currencycalculator.models;

import java.util.List;

public class Expression {

    private List<ExpressionPart> expressionParts;
    private Expression subExpression;

    public List<ExpressionPart> getExpressionParts() {
        return expressionParts;
    }

    public void setExpressionParts(List<ExpressionPart> expressionParts) {
        this.expressionParts = expressionParts;
    }

    public Expression getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(Expression subExpression) {
        this.subExpression = subExpression;
    }
}
