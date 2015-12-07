package app.com.example.grace.currencycalculator.Controller.Calculator1;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;

public class CalculatorParser {
    Tokenizer tokenizer;
    transient LinkedList<Object>stack;
    transient Token lookahead;

    void match(String token) throws Exception {
        if(token == null || lookahead.getValue().equals(token)){
            try {
                lookahead = tokenizer.getNextToken();
            }
            catch(Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    void parseNumber() throws Exception {
        double number;

        if(lookahead == null) {
            throw new Exception();
        }

        try {
            number = Double.parseDouble((String)lookahead.getValue());
            stack.push(new Double(number));
        }
        catch (NumberFormatException numberFormatException){
            numberFormatException.printStackTrace();
        }
        match(null);
    }

    void parseFactor() throws Exception {
        if(lookahead == null) {
            throw new Exception();
        }
        if(lookahead.getType().equals(CalculatorTokenType.TOKEN_OPEN_BRACKET)) {
            match(CalculatorTokenType.OPENING_BRACKET);
            parseExpression();
            match(CalculatorTokenType.CLOSING_BRACKET);
        } else {
            parseNumber();
        }
    }

    void parseTerm() throws Exception {
        Token token;
        while(lookahead!=null) {
            if(lookahead.getType().equals(CalculatorTokenType.TOKEN_MULTIPLICATIVE)) {
                token = lookahead;
                match(lookahead.getValue());
                stack.push(CalculatorOperator.getOperator(token.getValue()));
            } else {
                break;
            }
        }
    }

    void parseExpression() throws Exception {
        Token token;
        parseTerm();
        while(lookahead != null) {
            if(lookahead.getType().equals(CalculatorTokenType.TOKEN_ADDITIVE)){
                token = lookahead;
                match(lookahead.getValue());
                parseTerm();
                stack.push(CalculatorOperator.getOperator(token.getValue()));
            } else {
                    break;
            }
        }
    }

    public CalculatorParser() {
        tokenizer = new Tokenizer(Arrays.asList((TokenType[])CalculatorTokenType.values()));
    }

    public LinkedList<Object> parse(String input) throws Exception {
        tokenizer.setInput(input);
        match(null);
        if (lookahead == null) {
            throw new Exception();
        }
        stack = new LinkedList<Object>();
        parseExpression();

        if(lookahead != null) {
            throw new Exception();
        }
        return stack;
    }
}
