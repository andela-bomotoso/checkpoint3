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
       private Stack expressionStack;
       private Stack subExpressionStack;

    public ExpressionAnalyzer(Context context,String destinationCurrency) {
        this.context = context;
        this.destinationCurrency = destinationCurrency;
        exchangeRateDbHelper = new ExchangeRateDbHelper(context);
    }

    public ExpressionAnalyzer(){

    }

    public Expression breakDownExpression(String expressionString) {
        initializeVariables();
        preBreakDown(expressionString,expressionStack);
        return analyzeExpressionStack(expressionStack);
    }

    private Expression analyzeExpressionStack(Stack expressionStack) {
        char peek = (char)expressionStack.peek();

        while (!expressionStack.empty()) {
            char current = (char) expressionStack.pop();
            checkCharacters(current,peek);
        }

        return getExpression(subExpressionStack);
    }

    private void checkCharacters(char current,char peek) {
        if(current == '-' && current == peek ) {
            updateCurrentExpressionString(current);

        } else if (Character.isLetter(current)) {
            analyzeCurrency(current);

        } else if (current == '(') {
            analyzeOpenParenthesis(current);

        } else if (!isBracketOpen && !isOperator(current) && !Character.isLetter(current)) {
            analyzeNumericCharacter(current);

        } else if (isBracketOpen && current != ')') {
            analyzeSubExpNumericCharacter(current);

        } else if (current == ')') {
            analyzeClosingParenthesis(current);

        } else if (isOperator(current) && current != peek) {
            analyzeOperator(current);
        }

        previous = current;
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
        expressionStack = new Stack();
        subExpressionStack = new Stack();
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
        if(!subexpression.equals("") & !subexpression.equals("(")) {
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
    private void updateCurrentExpressionString(char current) {
        currentExpressionString += current;
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

    private Expression getExpression(Stack subExpressionStack) {
        updateExpressionPartWithOperand();
        updateExpressionPartWithSubExpression(subExpressionStack);
        expression.setExpressionParts(expressionParts);
        return expression;
    }

    private void preBreakDown(String expressionString, Stack stack) {
        loadStack(expressionString, stack);
    }

    private void updateSubExpressionStack() {
        double currentExpressionValue = getRateValue(popOutLastOperand(subExpressionStack));
        subExpressionStack.push(new StringBuilder(currentExpressionValue + "").reverse().toString());
    }

    private void analyzeOpenParenthesis(char current) {
        isBracketOpen = true;
        updateSubExpressionString(subExpressionStack, current);

        if(!isOperator(previous) && previous!='\0') {
            updateExpressionPartWithOperand(currentExpressionString);
            updateExpressionPartWithOperator('*');
        }
        else {
            updateExpressionPartWithOperand(currentExpressionString);
        }
    }

    private void analyzeNumericCharacter(char current) {
        if(previous == ')') {
            updateExpressionPartWithOperator('*');
        }
        updateCurrentExpressionString(current);
    }

    private void analyzeSubExpNumericCharacter(char current) {
        updateSubExpressionString(subExpressionStack, current);
        if(expressionStack.empty()) {
            updateExpressionPartWithSubExpression(subExpressionStack);
        }
    }

    private void analyzeClosingParenthesis(char current) {
        isBracketOpen = false;
        updateSubExpressionString(subExpressionStack, current);
        updateExpressionPartWithSubExpression(subExpressionStack);
    }

    private void analyzeOperator(char current) {
        updateExpressionPartWithOperand(currentExpressionString);
        updateExpressionPartWithOperator(current);
    }

    private void analyzeCurrency(char current) {
        sourceCurrency = current + currencyLookAhead(expressionStack);

        if(!isBracketOpen) {
            currentExpressionString = getRateValue(currentExpressionString) + "";
        } else {
            updateSubExpressionStack();
        }
    }

}
