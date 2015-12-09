package app.com.example.grace.currencycalculator.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import app.com.example.grace.currencycalculator.data.ExchangeRateContract;
import app.com.example.grace.currencycalculator.data.ExchangeRateDbHelper;
import app.com.example.grace.currencycalculator.data.ExchangeRateProvider;
import app.com.example.grace.currencycalculator.models.ExchangeRate;

public class ExchangeRatesFetcher extends AsyncTask<String, Void, String> {

    ExchangeRateProvider exchangeRateProvider;
    Context context;

    public ExchangeRatesFetcher(Context context){
        this.context = context;
        exchangeRateProvider = new ExchangeRateProvider(context);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
                    connectToApi();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

    public String connectToApi() throws IOException {
        String buffer = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {

            final String EXCHANGE_RATE_BASE_URL = "https://www.exchangerate-api.com/";
            String url_query = EXCHANGE_RATE_BASE_URL + "USD" + "/" + "NGN" + "?k=" + BuildConfig.EXCHANGE_RATE_API_KEY;
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
