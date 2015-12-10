package app.com.example.grace.currencycalculator.Controller;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;
import app.com.example.grace.currencycalculator.models.ExchangeRate;
import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class ExpressionAnalyzer {
        Context context;
        Expression expression;
        String currentExpressionString;
        String sourceCurrency;
        ExchangeRateDbHelper exchangeRateDbHelper;;


        String destinationCurrency;
        String subexpression;
        boolean isBracketOpen;
        List<ExpressionPart> expressionParts;
        char current;
        char previous;
        int index;
        boolean currency;

    public ExpressionAnalyzer(Context context,String destinationCurrency) {
        this.context = context;
        this.destinationCurrency = destinationCurrency;
        exchangeRateDbHelper = new ExchangeRateDbHelper(context);
    }

    public ExpressionAnalyzer(){

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

            if(Character.isLetter(current)) {
                currency = true;
                sourceCurrency = sourceCurrency+current;
                continue;
            }

            if(currency && !isBracketOpen) {
                double currentExpressionValue = Double.parseDouble(currentExpressionString) * getExchangeRate(sourceCurrency, destinationCurrency);
                currentExpressionString = currentExpressionValue + "";
                currency = false;
                sourceCurrency = "";
            }
            if(currency && isBracketOpen) {
                double subExpressionValue = Double.parseDouble(currentExpressionString) * getExchangeRate(sourceCurrency, destinationCurrency);
                subexpression = subExpressionValue + "";
                currency = false;
                sourceCurrency = "";
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
        currentExpressionString = "";
        subexpression = "";
        isBracketOpen = false;
        sourceCurrency = "";
        expression = new Expression();
    }

    public boolean expressionStartsWithUnaryOperator() {
        return (index == 0 && current == '-');
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

    public boolean isCurrencyOperand(String str) {
        return str.length()>3 && Character.isDigit(str.charAt(str.length()-1));
    }

    public double getExchangeRate(String source, String destination) {
       String rate = exchangeRateDbHelper.query(source,destination);
        return Double.parseDouble(rate);
    }


}
