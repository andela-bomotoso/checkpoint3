package app.com.example.grace.currencycalculator.models;


public class ExchangeRate {

    private double value;

    private String source;

    private String destination;

    public ExchangeRate() {

    }

    public ExchangeRate(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
