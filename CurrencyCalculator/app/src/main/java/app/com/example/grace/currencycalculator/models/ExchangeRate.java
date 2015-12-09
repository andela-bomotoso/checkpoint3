package app.com.example.grace.currencycalculator.models;


import android.content.Context;

import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;

public class ExchangeRate {
    private Context context;
    private double value;

    private String source;

    private String destination;

//    private ExchangeRateDbHelper exchangeRateDbHelper = new ExchangeRateDbHelper(context);


    public ExchangeRate(Context context) {
        this.context = context;
    }

    public ExchangeRate(String source, String destination) {
        this.source = source;
        this.destination = destination;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getExchangeRate() {
        //return exchangeRateDbHelper.query(source,destination);
        return 0;
    }

}
