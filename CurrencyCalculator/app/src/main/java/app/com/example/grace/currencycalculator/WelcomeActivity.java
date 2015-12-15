package app.com.example.grace.currencycalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.com.example.grace.currencycalculator.controller.ExchangeRatesFetcher;
import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;

public class WelcomeActivity extends AppCompatActivity {
    ExchangeRatesFetcher exchangeRatesFetcher ;
    ExchangeRateDbHelper exchangeRateDbHelper;
    TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressText = (TextView)findViewById(R.id.progress_text);

        exchangeRatesFetcher = new ExchangeRatesFetcher(this);
        exchangeRateDbHelper = new ExchangeRateDbHelper(this);

        if(exchangeRateDbHelper.tableRows() == 0){
            exchangeRatesFetcher.execute();
        } else
        {
            Intent myIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            WelcomeActivity.this.startActivity(myIntent);
        }

    }

}
