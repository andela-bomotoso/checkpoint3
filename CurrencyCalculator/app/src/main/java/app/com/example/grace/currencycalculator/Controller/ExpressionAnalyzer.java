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
       private boolean isBracketOpen;
       private char previous;

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
        Stack subExpressionStack = new Stack();
        loadStack(expressionString,expressionStack);
        char peek = (char)expressionStack.peek();

        while (!expressionStack.empty()) {
           char current = (char) expressionStack.pop();
            if(current == '-' && current == peek ) {
                currentExpressionString += current;
            }
            else if (Character.isLetter(current)) {
                sourceCurrency = current + currencyLookAhead(expressionStack);

                if(!isBracketOpen) {
                    double currentExpressionValue = getRateValue(currentExpressionString);
                    currentExpressionString = currentExpressionValue + "";
                }
                else {
                    String currencyOperand =  popOutLastOperand(subExpressionStack);
                    double currentExpressionValue = getRateValue(currencyOperand);
                    subExpressionStack.push(new StringBuilder(currentExpressionValue+"").reverse().toString());
                }
            }
            else if (current == '(') {
                isBracketOpen = true;
                updateSubExpressionString(subExpressionStack,current);

                if(!isOperator(previous) && previous!='\0') {
                    updateExpressionPartWithOperand(currentExpressionString);
                    updateExpressionPartWithOperator('*');
                }
                else {
                    updateExpressionPartWithOperand(currentExpressionString);
                }

            } else if (!isBracketOpen && !isOperator(current) && !Character.isLetter(current)) {
                if(previous == ')') {
                    updateExpressionPartWithOperator('*');
                }
                currentExpressionString += current;

            } else if (isBracketOpen && current != ')') {
               updateSubExpressionString(subExpressionStack,current);
                if(expressionStack.empty()) {
                    updateExpressionPartWithSubExpression(subExpressionStack);
                }

            } else if (current == ')') {
                isBracketOpen = false;
                updateSubExpressionString(subExpressionStack,current);
                updateExpressionPartWithSubExpression(subExpressionStack);

            } else if (isOperator(current) && current!= peek) {
                updateExpressionPartWithOperand(currentExpressionString);
                updateExpressionPartWithOperator(current);
            }
            previous = current;
        }
        updateExpressionPartWithOperand();
        updateExpressionPartWithSubExpression(subExpressionStack);
        expression.setExpressionParts(expressionParts);
        return expression;
    }

    private Stack loadStack(String str,Stack expressionStack) {
            for(int i = str.length()-1; i >= 0; i--) {
                expressionStack.push(str.charAt(i));
            }
        return expressionStack;
        }

    private String currencyLookAhead(Stack expressionStack) {
            return expressionStack.pop()+"" +  expressionStack.pop();
        }

    private void initializeVariables() {

        expressionParts = new ArrayList<>();
        currentExpressionString = "";
        subexpression = "";
        isBracketOpen = false;
        sourceCurrency = "";
        expression = new Expression();
        previous = '\0';
        currentExpressionString = "";
    }

    private boolean isOperator(char expressionPart) {
        return (expressionPart == ('+')||expressionPart == ('-')||expressionPart == ('*')||expressionPart == ('/' ));
    }

    private boolean currentExpressionStringEmpty() {
        return currentExpressionString == "";
    }

    private void updateExpressionPartWithOperand() {
        if (!currentExpressionStringEmpty()) {
            expressionParts.add(new Operand(currentExpressionString));
            clearCurrentExpressionString();
        }
    }

    private void updateExpressionPartWithSubExpression(Stack subexpressionStack) {
        while (!subexpressionStack.empty()){
            subexpression+=subexpressionStack.pop();
        }
        if(subexpression != "") {
            expressionParts.add(new Operand(new StringBuilder(subexpression).reverse().toString()));
            subexpression = "";
        }
    }

    private void clearCurrentExpressionString(){
        currentExpressionString = "";
    }

    private double getExchangeRate(String source, String destination) {
       String rate = exchangeRateDbHelper.query(source,destination);
        return Double.parseDouble(rate);
    }

    private double getRate(String str, String source, String destinationCurrency) {
       return Double.parseDouble(str) * getExchangeRate(source, destinationCurrency);
    }

    private void updateExpressionPartWithOperand(String str) {
        if (!str.equals("")) {
            expressionParts.add(new Operand(str));
            clearCurrentExpressionString();
        }
    }

    private void updateExpressionPartWithOperator(char current) {
        expressionParts.add(new Operator(current + ""));
    }

    public void updateSubExpressionString(Stack subexpressionStack, char current) {
        subexpressionStack.push(current);
    }

    private String popOutLastOperand(Stack subexpressionStack) {
        String lastOperand  = "";
        while (!subexpressionStack.empty()) {
            char current = (char)subexpressionStack.pop();
            if(Character.isDigit(current)){
            lastOperand += current;
            } else {
                subexpressionStack.push(current);
                break;
            }
        }
        return new StringBuilder(lastOperand).reverse().toString();
    }

    private double getRateValue(String str) {
        return Double.parseDouble(str) * getExchangeRate(sourceCurrency,destinationCurrency);
    }
}
