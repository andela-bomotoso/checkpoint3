package app.com.example.grace.currencycalculator.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncAdapterType;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.com.example.grace.currencycalculator.Controller.ExchangeRateMap;
import app.com.example.grace.currencycalculator.Controller.ExchangeRatesFetcher;
import app.com.example.grace.currencycalculator.models.ExchangeRate;

public class ExchangeRateProvider {




    ExchangeRateDbHelper exchangeRateDbHelper;

    Context context;
    public ExchangeRateProvider(Context context){
        this.context = context;
    }

    public int query(String source, String destination) {

        String query = "SELECT rate FROM exchange_rate where SOURCE ='"+source+"' and DESTINATION ='" +destination+"'";
        //String query = "SELECT rate FROM exchange_rate";
        SQLiteDatabase sqLiteDatabase = new ExchangeRateDbHelper(context).getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        c.moveToFirst();

//        if(c.moveToFirst()) {
//            return c.getString(c.getColumnIndex("rate"));
//        }
//        Log.v("Result",c.getString(c.getColumnIndex("rate")));
        return c.getCount();
    }

    public int bulkInsert(List<ExchangeRateMap<String,String,String>> currenciesRate) {

        SQLiteDatabase sqLiteDatabase = new ExchangeRateDbHelper(context).getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        int returnCount = 0;

        for(ExchangeRateMap currentMap: currenciesRate) {

            String source = currentMap.source.toString();
            String destination = currentMap.destination.toString();
            String rate = currentMap.rate.toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_SOURCE, source);
            contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_DESTINATION, destination);
            contentValues.put(ExchangeRateContract.ExchangeRates.COLUMN_RATE,rate);

            long id = sqLiteDatabase.insert(ExchangeRateContract.ExchangeRates.TABLE_NAME, null, contentValues);

            if (id != -1) {
                returnCount++;
            }
        }
        Log.v("Result Count",returnCount+"");

        return returnCount;

    }



}
