package app.com.example.grace.currencycalculator.Controller;

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

public class ExchangeRatesFetcher extends AsyncTask<String, Void, String[]> {
    Context context;
    List<ExchangeRateMap<String,String,String>> sourceDestinationRates = new ArrayList<ExchangeRateMap<String,String,String>>();

    public ExchangeRatesFetcher(Context context){
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... params) {

        List<ExchangeRateMap<String,String,String>> exchangeRateMaps = mapCurrencyCodes();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {

            for(ExchangeRateMap exchangeRateMap:exchangeRateMaps) {
                String source = exchangeRateMap.source.toString();
                String destination = exchangeRateMap.destination.toString();
            final String EXCHANGE_RATE_BASE_URL = "https://www.exchangerate-api.com/";
            String url_query = EXCHANGE_RATE_BASE_URL + source + "/" + destination + "?k=" + BuildConfig.EXCHANGE_RATE_API_KEY;
            URL url = new URL(url_query);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            exchangeRateMap.rate = buffer.toString();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
                exchangeRateMap.rate = buffer.toString();
                sourceDestinationRates.add(exchangeRateMap);
                //Log.v("Mapper Result",exchangeRateMap.source+" "+exchangeRateMap.destination+ " "+exchangeRateMap.rate);
        }

        } catch (IOException ioException) {
            return null;
        } finally {
            if (urlConnection == null) {
                urlConnection.disconnect();
            }
        }
        return null;

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

    public List<ExchangeRateMap<String,String,String>> getSourceDestinationRates() {
        for(ExchangeRateMap e:sourceDestinationRates)
            Log.v("MapperResult:",e.source+" "+e.destination+" "+e.rate);
        return sourceDestinationRates;
    }





}
