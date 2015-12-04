package app.com.example.grace.currencycalculator.models;


public class ExchangeRate {

    private double value;

    private Currency source;

    private Currency destination;

    public ExchangeRate() {

    }

    public ExchangeRate(Currency source, Currency destination) {
        this.source = source;
        this.destination = destination;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getSource() {
        return source;
    }

    public void setSource(Currency source) {
        this.source = source;
    }

    public Currency getDestination() {
        return destination;
    }

    public void setDestination(Currency destination) {
        this.destination = destination;
    }
}
