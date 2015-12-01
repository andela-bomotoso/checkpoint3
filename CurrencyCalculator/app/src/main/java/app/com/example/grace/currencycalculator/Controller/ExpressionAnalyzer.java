package app.com.example.grace.currencycalculator.Controller;


import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class ExpressionAnalyzer {

    public ExpressionAnalyzer() {

    }
    public Expression parseToken(String expressionString) {

        Expression expression = new Expression();;
        Validator validator = new Validator();
        String str = "";
        List<ExpressionPart> expressionParts = new ArrayList<>();

        for (int i = 0; i < expressionString.length(); i++) {

            if (validator.isOperator(expressionString.charAt(i))) {
                expressionParts.add(new Operand(str));
                expressionParts.add(new Operator(expressionString.charAt(i) + ""));
                str = "";
            } else {
                str = str + expressionString.charAt(i);
            }
        }

        expressionParts.add(new Operand(str));
        expression.setExpressionParts(expressionParts);

        return expression;
    }
}
