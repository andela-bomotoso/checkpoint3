package app.com.example.grace.currencycalculator.models;

public class Operand extends ExpressionPart {

    Currency sourceCurrency;

    public Operand() {
    }

    public Operand(String value) {
        setValue(value);
    }

    @Override
    public boolean isOperand() {
        return true;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }
}
