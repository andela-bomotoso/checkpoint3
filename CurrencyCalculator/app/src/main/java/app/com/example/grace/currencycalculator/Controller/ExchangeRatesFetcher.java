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

public class ExchangeRatesFetcher extends AsyncTask<String, Void, List<ExchangeRateMap<String,String,String>>> {

    ExchangeRateProvider exchangeRateProvider;
    Context context;
    ContentValues contentValues;
    ContentValues[] values = new ContentValues[144];
    ExchangeRateDbHelper dbhelper;

    public ExchangeRatesFetcher(Context context){
        this.context = context;
        exchangeRateProvider = new ExchangeRateProvider(context);
    }

    @Override
    protected List<ExchangeRateMap<String,String,String>> doInBackground(String... params) {
        dbhelper = new ExchangeRateDbHelper(context);
        int count = 0;
        List<String> currencies;
        currencies = getCurrencyCodes();
        for (int i = 0; i < currencies.size() ; i++) {
            for (int j = 0; j <currencies.size() ; j++) {
                ExchangeRate exrate = new ExchangeRate(currencies.get(i), currencies.get(j));
                String result = null;
                try {
                    result = connectToApi(exrate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                exrate.setValue(Double.parseDouble(result));

                contentValues = new ContentValues();
                contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_SOURCE, exrate.getSource());
                contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_DESTINATION, exrate.getDestination());
                contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_RATE,exrate.getValue());
                values[count] = contentValues;
                count++;
                Log.v("Exchange Rate",(exrate.getValue()+""));
            }

        }

        dbhelper.bulkInsert(ExchangeRateContract.ExchangeRates.CONTENT_URI, values);
        return null;
    }

    public String connectToApi(ExchangeRate rate) throws IOException {
        String buffer = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String source = rate.getSource();
        String destination = rate.getDestination();
        try {

            final String EXCHANGE_RATE_BASE_URL = "https://www.exchangerate-api.com/";
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

    public  List<ExchangeRateMap<String,String,String>> mapCurrencyCodes() {

        String str = "";
        List<ExchangeRateMap<String,String,String>> exchangeRateMaps = new ArrayList<ExchangeRateMap<String,String,String>>();
        List<String> currencies = getCurrencyCodes();
        String source = "";
        String destination = "";

        for(int i = 0; i<currencies.size();i++) {
            source = currencies.get(i);
            for(int j = 0; j<currencies.size();j++) {
                destination = currencies.get(j);
                ExchangeRateMap exchangeRateMap = new ExchangeRateMap(source,destination,"");
                exchangeRateMaps.add(exchangeRateMap);
            }
        }
        return exchangeRateMaps;
    }

//    public List<ExchangeRateMap<String,String,String>> getSourceDestinationRates() {
//        for(ExchangeRateMap e:sourceDestinationRates)
//            Log.v("MapperResult:",e.source+" "+e.destination+" "+e.rate);
//        return sourceDestinationRates;
//    }





}
