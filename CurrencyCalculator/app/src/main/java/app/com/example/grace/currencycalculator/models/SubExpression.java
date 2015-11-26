package app.com.example.grace.currencycalculator.models;

public class SubExpression extends Expression {

    String value;

    public SubExpression() {

    }

    public SubExpression(String value) {
        setValue(value);
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
