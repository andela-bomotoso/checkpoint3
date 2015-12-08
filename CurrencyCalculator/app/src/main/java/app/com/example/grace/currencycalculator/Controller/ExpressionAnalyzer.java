package app.com.example.grace.currencycalculator.Controller;


import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class ExpressionAnalyzer {

    Expression expression;
    String currentExpressionString;
    String expressionString;

    String subexpression;
    boolean isBracketOpen;
    List<ExpressionPart> expressionParts;
    char current;
    char previous;
    int index;


    public ExpressionAnalyzer() {
    }

    public Expression breakDownExpression(String expressionString) {

        initializeVariables();

        for (index = 0; index < expressionString.length(); index++) {

            current = expressionString.charAt(index);

            if(index > 0 ) {
                previous = expressionString.charAt(index-1);
            }

            if(expressionStartsWithUnaryOperator()) {
                updateCurrentExpressionString();
                continue;
            }

            if (isOperator(current) & !isBracketOpen) {
                    updateExpressionPartWithOperand();
                    updateExpressionPartWithOperator();
            }

            else if(currentExpressionPartIsOpeningBracket()) {
                isBracketOpen = true;

                if( index > 0 && !isOperator(previous)) {
                    updateExpressionPartWithOperand();
                    updateExpressionPartWithOperator("*");
                }
                updateSubExpressionString();
            }

            else if (isBracketOpen && current != ')') {
                updateSubExpressionString();
                if(index==expressionString.length()-1) {
                    updateExpressionPartWithSubExpression();
                }
            }

            else if(current == ')' ) {
                if(currentExpressionString != "") {
                    expressionParts.add(new Operand(currentExpressionString));
                    currentExpressionString = "";
                }
                isBracketOpen = false;
                subexpression += current;
                expressionParts.add(new Operand(subexpression));
                subexpression = "";
            }
            else if (!isBracketOpen && !isOperator(current) & current != ')' & current != '(') {

                if(previous == ')') {
                    updateExpressionPartWithOperator("*");
                }
                currentExpressionString = currentExpressionString + expressionString.charAt(index);
            }
        }

        if(currentExpressionString != "") {
            expressionParts.add(new Operand(currentExpressionString));
        }
        expression.setExpressionParts(expressionParts);

        return expression;
    }

    public void initializeVariables() {

        expressionParts = new ArrayList<>();
        expression = new Expression();
        currentExpressionString = "";
        subexpression = "";
        isBracketOpen = false;
    }

    public boolean expressionStartsWithUnaryOperator() {
        return (index == 0 && isOperator(current));
    }

    public boolean isOperator(char expressionPart) {

        return (expressionPart == ('+')||expressionPart == ('-')||expressionPart == ('*')||expressionPart == ('/' ));
    }

    public void updateCurrentExpressionString() {
        currentExpressionString += current;
    }

    public boolean currentExpressionStringEmpty() {
        return currentExpressionString == "";
    }

    public void updateExpressionPartWithOperand() {
        if (!currentExpressionStringEmpty()) {
            expressionParts.add(new Operand(currentExpressionString));
            clearCurrentExpressionString();
        }
    }

    public void updateExpressionPartWithOperator() {
        expressionParts.add(new Operator(current + ""));
    }
    public void updateExpressionPartWithOperator(String operator) {
        expressionParts.add(new Operator(operator));
    }

    public void updateExpressionPartWithSubExpression() {
        expressionParts.add(new Operand(subexpression));
        subexpression="";
    }
    public void clearCurrentExpressionString(){
        currentExpressionString = "";
    }
    public boolean currentExpressionPartIsOpeningBracket() {
        return current =='(';
    }
    public boolean currentExpressionPartIsClosingBracket() {
        return current ==')';
    }
    public void updateSubExpressionString() {
        subexpression += current;
    }
    public void clearSubExpressionString() {
        subexpression = "";
    }
    public boolean subExpression() {
       return false;
    }


}
