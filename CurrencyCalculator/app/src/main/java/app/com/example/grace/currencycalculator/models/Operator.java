package app.com.example.grace.currencycalculator.models;

public class Operator extends ExpressionPart {

    String value;

    public Operator() {

    }

    public boolean isOperator() {
        return true;
    }

    public boolean isOperand() {
        return false;
    }
}
