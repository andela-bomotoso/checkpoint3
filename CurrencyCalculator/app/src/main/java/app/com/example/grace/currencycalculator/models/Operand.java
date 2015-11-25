package app.com.example.grace.currencycalculator.models;

public class Operand extends ExpressionPart {

    public Operand() {

    }

    public boolean isOperand() {
        return true;
    }

    public boolean isOperator() {
        return false;
    }

}
