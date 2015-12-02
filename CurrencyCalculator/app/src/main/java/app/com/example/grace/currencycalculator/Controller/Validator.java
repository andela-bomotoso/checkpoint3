package app.com.example.grace.currencycalculator.Controller;

import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;

public class Validator {

    String expression;
    ExpressionAnalyzer expressionAnalyzer;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public  boolean validate(char keyPressed) {

        return (!startWithInvalidCharacter(keyPressed)) && (!isDivisionByZero(keyPressed)) && (!isRepeatedZeros(keyPressed))
                && (!isRepeatedDecimals(keyPressed)) && (!isMismatchedBrackets(keyPressed));
    }

    public boolean isOperator(char expressionPart) {
        return (expressionPart == ('+')||expressionPart == ('-')||expressionPart == ('*')||expressionPart == ('/' ));
    }

    private boolean startWithInvalidCharacter(char keyPressed) {

        return (expression.isEmpty() && (isOperator(keyPressed) || keyPressed == ')'));
    }

    public String validateOperator(char keyPressed) {

        if (!startWithInvalidCharacter(keyPressed)) {

            if (expression.length() >= 1) {
                char previous = expression.charAt(expression.length() - 1);

                if (isOperator(previous) && isOperator(keyPressed) && previous != ' ') {
                    expression = (expression.substring(0, expression.length() - 1));
                }
            }
            expression = expression + keyPressed;
        }
        return expression;
    }

    public boolean isDivisionByZero(char keyPressed) {
        return expression.length() > 1 && keyPressed=='0' && expression.charAt(expression.length() - 1) == '/';
    }

    public boolean isRepeatedZeros(char keyPressed) {
        return expression.length() > 1 && keyPressed=='0' && expression.charAt(expression.length() - 1) == '0'
                && isOperator(expression.charAt(expression.length() - 2));
    }

    public boolean isRepeatedDecimals(char keyPressed) {

        expressionAnalyzer = new ExpressionAnalyzer();
        Expression currentExpression = expressionAnalyzer.breakDownExpression(expression);
        List<ExpressionPart> expressionParts = currentExpression.getExpressionParts();
        String previousExpressionPart =  expressionParts.get(expressionParts.size()-1).getValue();
        return previousExpressionPart.contains(".") && keyPressed == '.';
    }

    public boolean isMismatchedBrackets(char keyPressed) {
        return (!expression.contains("("))  && keyPressed == ')';
    }
}
