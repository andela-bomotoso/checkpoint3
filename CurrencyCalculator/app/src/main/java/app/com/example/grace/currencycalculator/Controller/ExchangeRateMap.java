package app.com.example.grace.currencycalculator.Controller;

import javax.xml.transform.Source;

import app.com.example.grace.currencycalculator.models.ExchangeRate;

public class ExchangeRateMap<Source,Destination,Rate> {

    public Source source;
    public Destination destination;
    public Rate rate;

    public ExchangeRateMap(Source source, Destination destination, Rate rate) {
        this.source = source;
        this.destination = destination;
        this.rate = rate;
    }
}
