package app.com.example.grace.currencycalculator.models;

import java.util.List;

public class Expression {

    private List<ExpressionPart> expressionParts;

    public List<ExpressionPart> getExpressionParts() {
        return expressionParts;
    }

    public void setExpressionParts(List<ExpressionPart> expressionParts) {
        this.expressionParts = expressionParts;
    }
}
