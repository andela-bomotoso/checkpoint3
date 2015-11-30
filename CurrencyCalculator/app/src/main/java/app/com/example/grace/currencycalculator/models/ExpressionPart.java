package app.com.example.grace.currencycalculator.models;

public abstract class ExpressionPart {

    public ExpressionPart() {

    }

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isOperand() {
        return false;
    };

    public boolean isOperator() {
        return false;
    }

}
