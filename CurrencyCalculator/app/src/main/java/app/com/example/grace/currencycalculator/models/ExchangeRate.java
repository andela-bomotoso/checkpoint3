package app.com.example.grace.currencycalculator.models;


public class ExchangeRate {

    public ExchangeRate() {

    }

    public ExchangeRate(Currency sourceCurrency, Currency baseCurrency) {
        this.sourceCurrency = sourceCurrency;
        this.baseCurrency = baseCurrency;
    }

    private double value;

    private Currency sourceCurrency;

    private Currency baseCurrency;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}
