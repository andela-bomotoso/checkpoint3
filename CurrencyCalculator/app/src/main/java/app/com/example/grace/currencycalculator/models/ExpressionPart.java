package app.com.example.grace.currencycalculator.models;

public abstract class ExpressionPart {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public abstract boolean isOperand();

    public abstract boolean isOperator();
}
