package app.com.example.grace.currencycalculator.Controller.Calculator1;

import java.net.PortUnreachableException;
import java.util.regex.Pattern;

public enum  CalculatorTokenType implements TokenType {

    TOKEN_OPEN_BRACKET("\\("),
    TOKEN_CLOSE_BRACKET("\\("),
    TOKEN_ADDITIVE("[-+]"),
    TOKEN_MULTIPLICATIVE("[*/]"),
    TOKEN_NUMBER("\\d+(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

    public static final String ADDITION = "+";
    public static final String SUBTRACTION = "-";
    public static final String MULTIPLICATION = "*";
    public static final String DIVISION = "/";
    public static final String OPENING_BRACKET = "(";
    public static final String CLOSING_BRACKET = ")";

    private Pattern pattern;

    CalculatorTokenType(String pattern) {
    this.pattern = Pattern.compile(pattern);
    }

    public Object getType() {
        return this;
    }
    public Pattern getPattern() {
        return  this.pattern;
    }
}
