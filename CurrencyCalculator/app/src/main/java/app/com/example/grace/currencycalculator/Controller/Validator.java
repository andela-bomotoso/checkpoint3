package app.com.example.grace.currencycalculator.Controller;

import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;

public class Validator {

    String expression;
    //ExpressionAnalyzer expressionAnalyzer;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean validate(char keyPressed) {

        return (!startWithInvalidCharacter(keyPressed)) && (!isDivisionByZero(keyPressed)) && (!isRepeatedZeros(keyPressed))
                && (!isMismatchedBrackets(keyPressed)) && (!closingBracketAfterOperator(keyPressed))
                && (!repeatedOpeningBracket(keyPressed)) && (!isEmptyParenthesis(keyPressed)) && (!repeatedClosingBracket(keyPressed))
                 && (!openingBracketsDoesNotMatchClosingBrackets(keyPressed)) && (!operatorAfterOpeningBracket(keyPressed))
                && (!isOperandAfterCurrency(keyPressed));
        // && (!isRepeatedDecimals(keyPressed))
    }

    public boolean validateCurrency() {
        return (isLastCharacterADigit());
    }

    public boolean isOperator(char expressionPart) {

        return (expressionPart == ('+')||expressionPart == ('-')||expressionPart == ('*')||expressionPart == ('/' ));
    }

    private boolean startWithInvalidCharacter(char keyPressed) {

       // return (expression.isEmpty() &&  (keyPressed == ')'|| keyPressed == '+' || (keyPressed == '*' || (keyPressed == '/'))));
        return (expression.isEmpty() && !Character.isDigit(keyPressed) && keyPressed != '-' && keyPressed != '(');
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

//    public boolean isRepeatedDecimals(char keyPressed) {
//
//        String previousExpressionPart = "";
//        expressionAnalyzer = new ExpressionAnalyzer();
//        Expression currentExpression = expressionAnalyzer.breakDownExpression(expression);
//        List<ExpressionPart> expressionParts = currentExpression.getExpressionParts();
//        if (expressionParts.size() >= 1) {
//            previousExpressionPart = expressionParts.get(expressionParts.size() - 1).getValue();
//        }
//
//        return previousExpressionPart.contains(".") && keyPressed == '.';
//    }
    public boolean closingBracketAfterOperator(char keyPressed) {

        return (expression.length() > 1) && isOperator(expression.charAt(expression.length()-1)) && keyPressed == ')';
    }
    public boolean operatorAfterOpeningBracket(char keyPressed) {

        return (expression.length() > 1)  && (expression.charAt(expression.length()-1)== '(') && (isOperator(keyPressed));
    }
    public boolean repeatedOpeningBracket(char keyPressed) {

        return (expression.length() >= 1) && expression.charAt(expression.length()-1)=='(' && keyPressed == '(';
    }

    public boolean repeatedClosingBracket(char keyPressed) {

        return (expression.length() >= 1) && expression.charAt(expression.length()-1)==')' && keyPressed == ')';
    }

    public boolean isMismatchedBrackets(char keyPressed) {

        return (!expression.contains("(") && keyPressed == ')');
    }

    public boolean isEmptyParenthesis(char keyPressed) {

        return (expression.length() >= 1) && expression.charAt(expression.length()-1)=='(' && keyPressed == ')';
    }

    public boolean openingBracketsDoesNotMatchClosingBrackets(char keyPressed) {
        int openBrackets = 0,closeBrackets = 0;

        if(keyPressed == '(') {
            for (int i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == '(') {
                    openBrackets++;
                } else if (expression.charAt(i) == ')') {
                    closeBrackets++;
                }
            }
        }

        return openBrackets != closeBrackets;
    }


    public String removeBrackets(String subExpression) {
        if(subExpression.charAt(subExpression.length()-1) == ')') {
            subExpression = subExpression.substring(1, subExpression.length() - 1);
        }
        else {
            subExpression = subExpression.substring(1, subExpression.length());
        }
        return subExpression;

    }

    public boolean isLastCharacterADigit() {
        boolean ans = false;
       if (expression.length()>1) {
           ans = Character.isDigit(expression.charAt(expression.length() - 1));
       }
        else if(expression.length() == 1 && !expression.equals("-")) {
           ans = true;
       }

       return ans;
    }

    public boolean isOperandAfterCurrency(char keyPressed) {
        return expression.length() > 3 && Character.isLetter(expression.charAt(expression.length()-1)) && Character.isDigit(keyPressed);
    }

    public String getLastOperand() {
        return "";
    }
}
