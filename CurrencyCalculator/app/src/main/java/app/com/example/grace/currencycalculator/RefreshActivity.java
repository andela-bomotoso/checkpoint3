package app.com.example.grace.currencycalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import app.com.example.grace.currencycalculator.controller.ExchangeRatesFetcher;
import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;

public class RefreshActivity extends AppCompatActivity {
    ExchangeRatesFetcher exchangeRatesFetcher ;
    ExchangeRateDbHelper exchangeRateDbHelper;
    TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressText = (TextView) findViewById(R.id.progress_text);

        exchangeRatesFetcher = new ExchangeRatesFetcher(this);
        exchangeRateDbHelper = new ExchangeRateDbHelper(this);
    }

}
