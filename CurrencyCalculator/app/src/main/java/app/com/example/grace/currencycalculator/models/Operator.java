package app.com.example.grace.currencycalculator.models;

public class Operator extends ExpressionPart {

    public Operator() {

    }

    public Operator(String value){
        setValue(value);
    }

    @Override
    public boolean isOperator() {
        return true;
    }

}
