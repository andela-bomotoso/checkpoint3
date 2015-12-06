package app.com.example.grace.currencycalculator.data;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockApplication;

import junit.framework.TestCase;

import app.com.example.grace.currencycalculator.Controller.ExchangeRatesFetcher;



public class ExchangeRateProviderTest extends AndroidTestCase {
    ExchangeRateProviderTest application;
    ExchangeRatesFetcher exchangeRatesFetcher;
    ExchangeRateProvider exchangeRateProvider;

    Context context;
    String name;
    public ExchangeRateProviderTest(Context context){
        this.context = context;
    }

    public void setUp() {

       // ExchangeRateProviderTest context = Mockito.mock(ExchangeRateProviderTest.class);

       exchangeRatesFetcher = new ExchangeRatesFetcher(getContext());
        //exchangeRateProvider = new ExchangeRateProvider(context);

      exchangeRatesFetcher.execute();
      exchangeRatesFetcher.getSourceDestinationRates();
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

    }

    public void testBulkInsert() throws Exception {
        exchangeRatesFetcher.execute();
        exchangeRatesFetcher.getSourceDestinationRates();
        ExchangeRateProvider exchangeRateProvider = new ExchangeRateProvider(getContext());
        assertEquals(2500,exchangeRateProvider.bulkInsert());

    }
}