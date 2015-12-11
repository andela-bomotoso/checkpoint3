package app.com.example.grace.currencycalculator.models;

public class Operand extends ExpressionPart {

    public Operand() {
    }

    public Operand(String value) {
        setValue(value);
    }

    @Override
    public boolean isOperand() {
        return true;
    }
}
