package app.com.example.grace.currencycalculator.models;

import java.util.List;

public class Expression extends ExpressionPart {

    private List<ExpressionPart> expressionParts;
    private SubExpression subExpressionValue;

    public Expression() {

    }

    public List<ExpressionPart> getExpressionParts() {
        return expressionParts;
    }

    public void setExpressionParts(List<ExpressionPart> expressionParts) {
        this.expressionParts = expressionParts;
    }

    public SubExpression getSubExpression() {
        return subExpressionValue;
    }

    public void setSubExpression(SubExpression subExpressionvalue) {
        this.subExpressionValue = subExpressionvalue;
    }
}
