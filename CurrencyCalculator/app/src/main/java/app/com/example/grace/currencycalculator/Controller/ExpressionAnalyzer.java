package app.com.example.grace.currencycalculator.controller;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;
import app.com.example.grace.currencycalculator.models.Expression;
import app.com.example.grace.currencycalculator.models.ExpressionPart;
import app.com.example.grace.currencycalculator.models.Operand;
import app.com.example.grace.currencycalculator.models.Operator;

public class ExpressionAnalyzer {
       private Context context;
       private Expression expression;
       private ExchangeRateDbHelper exchangeRateDbHelper;
       private List<ExpressionPart> expressionParts;
       private String destinationCurrency;
       private String subexpression;
       private String currentExpressionString;
       private String sourceCurrency;
       private String subExpressionCurrencyOperand;
       private boolean isBracketOpen;
       private boolean currency;
       private char current;
       private char previous;
       private int index;
       private int count;

    public ExpressionAnalyzer(Context context,String destinationCurrency) {
        this.context = context;
        this.destinationCurrency = destinationCurrency;
        exchangeRateDbHelper = new ExchangeRateDbHelper(context);
    }

    public ExpressionAnalyzer(){

    }

    public Expression breakDownExpression(String expressionString) {

        initializeVariables();

        Stack expressionStack = new Stack();
        loadStack(expressionString,expressionStack);
        while (!expressionStack.empty()) {
            char current = (char) expressionStack.pop();

            if (current == '(') {
                isBracketOpen = true;

                if(!isOperator(previous) && previous!='\0') {
                    updateExpressionPartWithOperand(currentExpressionString);
                    updateExpressionPartWithOperator('*');
                }
                else {
                    updateExpressionPartWithOperand(currentExpressionString);
                }
                subexpression+=current;

            } else if (!isBracketOpen && !isOperator(current) && !Character.isLetter(current)) {
                if(previous == ')') {
                    updateExpressionPartWithOperator('*');
                }
                currentExpressionString += current;

            } else if (isBracketOpen && current != ')') {
                updateSubExpressionString(current);

            } else if (current == ')') {
                isBracketOpen = false;
                updateSubExpressionString(current);
                updateExpressionPartWithSubExpression();

            } else if (isOperator(current)) {
                updateExpressionPartWithOperand(currentExpressionString);
                updateExpressionPartWithOperator(current);
            }
            previous = current;
        }
        updateExpressionPartWithOperand();
        expression.setExpressionParts(expressionParts);
        return expression;
    }


        private Stack loadStack(String str,Stack expressionStack) {
            for(int i = str.length()-1; i >= 0; i--) {
                expressionStack.push(str.charAt(i));
            }
        return expressionStack;
        }

//        for (index = 0; index < expressionString.length(); index++) {
//
//            current = expressionString.charAt(index);
//
//            if(index > 0 ) {
//                previous = expressionString.charAt(index-1);
//            }
//
//            if(isBracketOpen && Character.isDigit(current)) {
//                subExpressionCurrencyOperand += current;
//            }
//
//            if(expressionStartsWithUnaryOperator()) {
//                updateCurrentExpressionString();
//                continue;
//            }
//
//            if(Character.isLetter(current)) {
//                count++;
//                currency = true;
//                sourceCurrency = sourceCurrency+current;
//                if(count < 3) {
//                    continue;
//                }
//            }
//
//            if(currency && !isBracketOpen) {
//                double currentExpressionValue = getRate(currentExpressionString, sourceCurrency, destinationCurrency);
//                currentExpressionString = currentExpressionValue + "";
//                resetCurrencyParameters();
//            }
//
//            if(currency && isBracketOpen) {
//                double subExpressionValue = getRate(subExpressionCurrencyOperand,sourceCurrency,destinationCurrency);
//                subexpression = removeLastSubexpresssion(subExpressionCurrencyOperand) + subExpressionValue+ "";
//                resetCurrencyParameters();
//                subExpressionCurrencyOperand = "";
//            }
//
//            if (isOperator(current) & !isBracketOpen) {
//                    updateExpressionPartWithOperand();
//                    updateExpressionPartWithOperator();
//            }
//
//            else if(currentExpressionPartIsOpeningBracket()) {
//                isBracketOpen = true;
//
//                if( index > 0 && !isOperator(previous)) {
//                    updateExpressionPartWithOperand();
//                    updateExpressionPartWithOperator("*");
//                }
//                updateSubExpressionString();
//            }
//
//            else if (isBracketOpen && current != ')') {
//                updateSubExpressionString();
//                if(index==expressionString.length()-1) {
//                    updateExpressionPartWithSubExpression();
//                }
//            }
//
//            else if(current == ')' ) {
//                updateExpressionPartWithOperand();
//                isBracketOpen = false;
//                subexpression += current;
//                updateExpressionPartWithSubExpression();
//            }
//            else if (!isBracketOpen && !isOperator(current) & current != ')' & current != '(') {
//
//                if(previous == ')') {
//                    updateExpressionPartWithOperator("*");
//                }
//                if (!Character.isLetter(expressionString.charAt(index))) {
//                    currentExpressionString = currentExpressionString + expressionString.charAt(index);
//                }
//            }
//        }
//
//        if(currentExpressionString != "") {
//            expressionParts.add(new Operand(currentExpressionString));
//        }
//        expression.setExpressionParts(expressionParts);

//        return expression;
//    }

    private void initializeVariables() {

        expressionParts = new ArrayList<>();
        currentExpressionString = "";
        subexpression = "";
        isBracketOpen = false;
        sourceCurrency = "";
        expression = new Expression();
        previous = '\0';
        current = '\0';
        subExpressionCurrencyOperand = "";
        count = 0;
    }

    private boolean expressionStartsWithUnaryOperator() {
        return (index == 0 && current == '-');
    }

    private boolean isOperator(char expressionPart) {

        return (expressionPart == ('+')||expressionPart == ('-')||expressionPart == ('*')||expressionPart == ('/' ));
    }

    private void updateCurrentExpressionString() {

        currentExpressionString += current;
    }

    private boolean currentExpressionStringEmpty() {
        return currentExpressionString == "";
    }

    private void updateExpressionPartWithOperand() {
        if (!currentExpressionStringEmpty()) {
            expressionParts.add(new Operand(currentExpressionString));
            clearCurrentExpressionString();
        }
        currency = false;
    }

    private void updateExpressionPartWithOperator() {
        expressionParts.add(new Operator(current + ""));
        currency = false;
    }

    private void updateExpressionPartWithOperator(String operator) {
        expressionParts.add(new Operator(operator));
        currency = false;
    }

    private void updateExpressionPartWithSubExpression() {
        expressionParts.add(new Operand(subexpression));
        subexpression="";
        currency = false;
    }

    private void clearCurrentExpressionString(){
        currentExpressionString = "";
    }

    private boolean currentExpressionPartIsOpeningBracket() {
        return current =='(';
    }

    public void updateSubExpressionString() {
        if(!currency) {
            subexpression += current;
        }
    }

    private double getExchangeRate(String source, String destination) {
       String rate = exchangeRateDbHelper.query(source,destination);
        return Double.parseDouble(rate);
    }

    private String removeLastSubexpresssion(String currencyOperand) {
        int len = currencyOperand.length();
        return subexpression.substring(0, subexpression.length() - len);
    }

    private void resetCurrencyParameters(){
        count = 0;
        sourceCurrency = "";
        currency = false;
        subExpressionCurrencyOperand = "";
    }

    private double getRate(String str, String source, String destinationCurrency) {
       return Double.parseDouble(str) * getExchangeRate(source, destinationCurrency);
    }


    private void updateExpressionPartWithOperand(String str) {
        if (!str.equals("")) {
            expressionParts.add(new Operand(str));
            clearCurrentExpressionString();
        }
        currency = false;
    }

    private void updateExpressionPartWithOperator(char current) {
        expressionParts.add(new Operator(current + ""));
        currency = false;
    }

    public void updateSubExpressionString(char current) {
            subexpression += current;

    }
}
