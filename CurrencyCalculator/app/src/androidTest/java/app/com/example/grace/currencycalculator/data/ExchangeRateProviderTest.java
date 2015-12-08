package app.com.example.grace.currencycalculator.data;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockApplication;
import android.util.Log;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.Controller.ExchangeRateMap;
import app.com.example.grace.currencycalculator.Controller.ExchangeRatesFetcher;



public class ExchangeRateProviderTest extends AndroidTestCase {
    ExchangeRateProviderTest application;
    ExchangeRatesFetcher exchangeRatesFetcher;
    ExchangeRateProvider exchangeRateProvider;
    ExchangeRateDbHelper exchangeRateDbHelper;

    Context context;
    String name;
    public ExchangeRateProviderTest(Context context){
        this.context = context;
    }

    public void setUp() {

       // ExchangeRateProviderTest context = Mockito.mock(ExchangeRateProviderTest.class);
        exchangeRateDbHelper = new ExchangeRateDbHelper(mContext);

       exchangeRatesFetcher = new ExchangeRatesFetcher(mContext);
        exchangeRateProvider = new ExchangeRateProvider(getContext());


      exchangeRatesFetcher.execute();

    }

    public ExchangeRateProviderTest(String name) {
        //super(name);
        this.name = name;

    }


    public void testUpdate() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testQuery() throws Exception {
       assertEquals(9, exchangeRateDbHelper.query("USD", "USD"));
      //  exchangeRateDbHelper.onCreate();
       // exchangeRateDbHelper.dropTable();
        //exchangeRateDbHelper.deleteTable();
    }


    public void testBulkInsert() throws Exception {

//        exchangeRatesFetcher.getSourceDestinationRates();
//        ExchangeRateProvider exchangeRateProvider = new ExchangeRateProvider(getContext());
//        assertEquals(2500,exchangeRateProvider.bulkInsert());

    }
}