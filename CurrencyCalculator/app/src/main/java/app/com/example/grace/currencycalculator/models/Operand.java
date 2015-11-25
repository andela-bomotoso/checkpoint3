package app.com.example.grace.currencycalculator.models;

public class Operand extends ExpressionPart {

    public Operand() {

    }

    @Override
    public boolean isOperand() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

}
