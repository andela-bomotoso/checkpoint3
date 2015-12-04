package app.com.example.grace.currencycalculator.models;

public class Operand extends ExpressionPart {

    private Currency source;

    public Operand() {
    }

    public Operand(String value) {
        setValue(value);
    }

    @Override
    public boolean isOperand() {
        return true;
    }

    public Currency getSource() {
        return source;
    }

    public void setSource(Currency source) {
        this.source = source;
    }
}
