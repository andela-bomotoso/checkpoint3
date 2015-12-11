package app.com.example.grace.currencycalculator.Controller;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;
import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class ExpressionAnalyzer {
        Context context;
        Expression expression;
        ExchangeRateDbHelper exchangeRateDbHelper;
        List<ExpressionPart> expressionParts;
        String destinationCurrency;
        String subexpression;
        String currentExpressionString;
        String sourceCurrency;
        String subExpressionCurrencyOperand = "";
        boolean isBracketOpen;
        boolean currency;
        char current;
        char previous;
        int index;
        int count = 0;

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

            if(isBracketOpen && Character.isDigit(current)) {
                subExpressionCurrencyOperand += current;
            }

            if(expressionStartsWithUnaryOperator()) {
                updateCurrentExpressionString();
                continue;
            }

            if(Character.isLetter(current)) {
                count++;
                currency = true;
                sourceCurrency = sourceCurrency+current;
                if(count < 3) {
                    continue;
                }
            }

            if(currency && !isBracketOpen) {
                double currentExpressionValue = Double.parseDouble(currentExpressionString) * getExchangeRate(sourceCurrency, destinationCurrency);
                currentExpressionString = currentExpressionValue + "";
                resetCurrencyParameters();
            }
            if(currency && isBracketOpen) {
                double subExpressionValue = Double.parseDouble(subExpressionCurrencyOperand) * getExchangeRate(sourceCurrency, destinationCurrency);
                subexpression = removeLastSubexpresssion(subExpressionCurrencyOperand) + subExpressionValue+ "";
                resetCurrencyParameters();
                subExpressionCurrencyOperand = "";
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
            else if(currentExpressionPartIsClosingBracket() ) {
                updateExpressionPartWithOperand();
                isBracketOpen = false;
                subexpression += current;
                updateExpressionPartWithSubExpression();
            }
            else if (!isBracketOpen && !isOperator(current) & currentExpressionPartIsParenthesis()) {

                if(previous == ')') {
                    updateExpressionPartWithOperator("*");
                }
                if (!Character.isLetter(expressionString.charAt(index))) {
                    currentExpressionString = currentExpressionString + expressionString.charAt(index);
                }
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
        currency = false;
    }

    public void updateExpressionPartWithOperator() {
        expressionParts.add(new Operator(current + ""));
        currency = false;
    }

    public void updateExpressionPartWithOperator(String operator) {
        expressionParts.add(new Operator(operator));
        currency = false;
    }

    public void updateExpressionPartWithSubExpression() {
        expressionParts.add(new Operand(subexpression));
        subexpression="";
        currency = false;
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

    public boolean currentExpressionPartIsParenthesis() {
        return current ==')' || current == '(';
    }

    public void updateSubExpressionString() {
        if(!currency) {
            subexpression += current;
        }
    }

    public void clearSubExpressionString() {
        subexpression = "";
    }

    public double getExchangeRate(String source, String destination) {
       String rate = exchangeRateDbHelper.query(source,destination);
        return Double.parseDouble(rate);
    }

    private String removeLastSubexpresssion(String currencyOperand) {
        int len = currencyOperand.length();
        return subexpression.substring(0,subexpression.length()-len);
    }

    private void resetCurrencyParameters(){
        count = 0;
        sourceCurrency = "";
        currency = false;
    }
}
