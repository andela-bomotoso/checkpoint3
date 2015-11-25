package app.com.example.grace.currencycalculator.models;

public class Operator extends ExpressionPart {

    public Operator() {

    }

    public boolean isOperator() {
        return true;
    }

    public boolean isOperand() {
        return false;
    }
}
