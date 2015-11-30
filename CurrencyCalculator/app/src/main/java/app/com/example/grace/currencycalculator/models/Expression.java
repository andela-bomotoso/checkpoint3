package app.com.example.grace.currencycalculator.models;

import java.util.List;

public class Expression  {

    private String value;

    private List<ExpressionPart> expressionParts;

    public Expression() {

    }

    public Expression(String value) {
        this.value = value;
    }

    public List<ExpressionPart> getExpressionParts() {
        return expressionParts;
    }

    public void setExpressionParts(List<ExpressionPart> expressionParts) {
        this.expressionParts = expressionParts;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
