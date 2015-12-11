package app.com.example.grace.currencycalculator.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.BuildConfig;
import app.com.example.grace.currencycalculator.R;
import app.com.example.grace.currencycalculator.Utilities;
import app.com.example.grace.currencycalculator.data.ExchangeRateContract;
import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;
import app.com.example.grace.currencycalculator.models.ExchangeRate;

public class ExchangeRatesFetcher extends AsyncTask<String, Void, String> {

    ExchangeRate exchangeRate = new ExchangeRate();
    String result="";
    int count = 0;
    Context context;
    ContentValues contentValues;
    ContentValues[] values = new ContentValues[900];
    ExchangeRateDbHelper dbhelper;

    public ExchangeRatesFetcher(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        dbhelper = new ExchangeRateDbHelper(context);
        List<String> currencies = getCurrencyCodes();
        for (int i = 0; i < currencies.size(); i++) {
            for (int j = 0; j < currencies.size(); i++) {
                exchangeRate = new ExchangeRate(currencies.get(i), currencies.get(j));
            }

            try {
                result = connectToApi(exchangeRate);
            } catch (IOException e) {
                e.printStackTrace();
            }
            exchangeRate.setRate(Double.parseDouble(result));

            contentValues = new ContentValues();
            contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_SOURCE, exchangeRate.getSource());
            contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_DESTINATION, exchangeRate.getDestination());
            contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_RATE, exchangeRate.getRate());
            values[count] = contentValues;
            count++;

        }
        dbhelper.updateTable(ExchangeRateContract.ExchangeRates.CONTENT_URI,values);
        return null;
    }

    public String connectToApi(ExchangeRate exchangeRate) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            String source = exchangeRate.getSource();
            String destination = exchangeRate.getDestination();
            final String EXCHANGE_RATE_BASE_URL = Utilities.ApiURl;
            String url_query = EXCHANGE_RATE_BASE_URL + source + "/" + destination + "?k=" + BuildConfig.EXCHANGE_RATE_API_KEY;
            URL url = new URL(url_query);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();


            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

        } catch (IOException ioException) {

        } finally {
            if (urlConnection == null) {
                urlConnection.disconnect();
            }
        }
        return reader.readLine();

    }

    public  List<String> getCurrencyCodes() {
        String str = "";
        CharSequence[] items = context.getResources().getStringArray(R.array.currency_codes);
        List<String> currencies = new ArrayList<>();
        for (int i = 0; i <= items.length - 1; i++) {
            currencies.add(items[i].toString());
            str+=items[i].toString()+" ";
        }
        return currencies;
    }

}
