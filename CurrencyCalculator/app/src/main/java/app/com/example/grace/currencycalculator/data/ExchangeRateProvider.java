package app.com.example.grace.currencycalculator.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
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

public class ExchangeRateProvider extends ContentProvider {

    ExchangeRateDbHelper exchangeRateDbHelper;



    Context context;
    public ExchangeRateProvider(Context context){
        this.context = context;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return null;
    }

    @Override
    public boolean onCreate() {

        exchangeRateDbHelper = new  ExchangeRateDbHelper(this.getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection,String[] selectionArgs) {

        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,String sortOrder) {

        SQLiteDatabase sqLiteDatabase = new ExchangeRateDbHelper(context).getWritableDatabase();

        //Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM exchange_rates+ WHERE source = '"+name+"'", null);

        //c.moveToNext();

       // tv.setText(c.getString(c.getColumnIndex("email")));

        return null;
    }

    public int bulkInsert() {

        ExchangeRatesFetcher exchangeRatesFetcher = new ExchangeRatesFetcher(getContext());
        List<ExchangeRateMap<String,String,String>> currenciesRate = exchangeRatesFetcher.getSourceDestinationRates();
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
