package app.com.example.grace.currencycalculator.Controller;

import app.com.example.grace.currencycalculator.models.ExpressionPart;

public class Validator {

    String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isOperator(char expressionPart) {
        return (expressionPart == ('+')||expressionPart == ('-')||expressionPart == ('*')||expressionPart == ('/' ));
    }

    private boolean startWithOperator(char keyPressed) {

        return (expression.isEmpty() && isOperator(keyPressed));
    }


    public  boolean validate(char keyPressed) {

        return (!startWithOperator(keyPressed)) && (!isDivisionByZero(keyPressed)) && (!isRepeatedZeros(keyPressed));
    }

    public String validateOperator(char keyPressed) {

        if (!startWithOperator(keyPressed)) {

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

    public boolean isRepeatedDecimals(char keypressed) {
        return true;
    }


}
