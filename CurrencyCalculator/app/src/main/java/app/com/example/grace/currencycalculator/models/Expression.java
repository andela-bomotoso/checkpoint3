package app.com.example.grace.currencycalculator.models;

import java.util.List;

public class Expression extends ExpressionPart {

    public Expression() {

    }

    private List<ExpressionPart> expressionParts;
    private SubExpression subExpression;

    public List<ExpressionPart> getExpressionParts() {
        return expressionParts;
    }

    public void setExpressionParts(List<ExpressionPart> expressionParts) {
        this.expressionParts = expressionParts;
    }

}
