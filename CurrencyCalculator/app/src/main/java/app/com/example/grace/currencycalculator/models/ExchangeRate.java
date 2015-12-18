package app.com.example.grace.currencycalculator.models;

public class ExchangeRate {

    private String source;
    private String destination;
    private double rate;

    public ExchangeRate(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public ExchangeRate(){

    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
