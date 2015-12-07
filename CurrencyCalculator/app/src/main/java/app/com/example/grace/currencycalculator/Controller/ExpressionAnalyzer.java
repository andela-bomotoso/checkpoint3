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

    public Expression breakDownExpression(String expressionString) {

        boolean isBracket = false;

        Expression expression = new Expression();
        Validator validator = new Validator();
        String str = "";
        String subexpression = "";
        List<ExpressionPart> expressionParts = new ArrayList<>();

        for (int i = 0; i < expressionString.length(); i++) {
            char current = expressionString.charAt(i);

            if( i == 0 && validator.isOperator(current)) {
                str += current;
                continue;
            }


            if (validator.isOperator(current) & !isBracket) {
                if (str != "") {
                    expressionParts.add(new Operand(str));
                }
                expressionParts.add(new Operator(current + ""));
                str = "";
            }
                else if(current == '(') {
                isBracket = true;
                if( i > 0 && !validator.isOperator(expressionString.charAt(i-1))) {

                    if(str != "") {
                        expressionParts.add(new Operand(str));
                        str = "";
                    }
                    expressionParts.add(new Operator("*"));
                }
                subexpression += current;
            }
            else if (isBracket && current != ')') {
                subexpression += expressionString.charAt(i);
                if(i==expressionString.length()-1) {
                    expressionParts.add(new Operand(subexpression));
                    subexpression="";
                }
            }

            else if(current == ')' ) {
                if(str != "") {
                    expressionParts.add(new Operand(str));
                    str = "";
                }
                isBracket = false;
                subexpression += current;
                expressionParts.add(new Operand(subexpression));
                subexpression = "";
            }
            else if (!isBracket && !validator.isOperator(current) & current != ')' & current != '(') {
                str = str + expressionString.charAt(i);
            }
        }

        if(str != "") {
            expressionParts.add(new Operand(str));
        }
        expression.setExpressionParts(expressionParts);

        return expression;
    }

}
