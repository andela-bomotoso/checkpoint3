package app.com.example.grace.currencycalculator.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.List;
import java.util.Map;

import app.com.example.grace.currencycalculator.MainActivity;
import app.com.example.grace.currencycalculator.controller.ExchangeRatesFetcher;
import app.com.example.grace.currencycalculator.controller.utilities.Utilities;


public class ExchangeRateDbHelper extends SQLiteOpenHelper {

    private Context context;
    private ExchangeRatesFetcher exchangeRatesFetcher;
    public static final String DATABASE_NAME = "exchange.db";
    static final int DATABASE_VERSION = 2;

    public ExchangeRateDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        exchangeRatesFetcher = new ExchangeRatesFetcher(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ExchangeRateContract.ExchangeRates.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void dropTable() {
        SQLiteDatabase database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_RATES_TABLE = "CREATE TABLE "+ ExchangeRateContract.ExchangeRates.TABLE_NAME + " (" +
                ExchangeRateContract.ExchangeRates.COLUMN_SOURCE + " TEXT, "+
                ExchangeRateContract.ExchangeRates.COLUMN_DESTINATION + " TEXT, " +
                ExchangeRateContract.ExchangeRates.COLUMN_RATE + " REAL)";
        sqLiteDatabase.execSQL(SQL_CREATE_RATES_TABLE);
    }

    public String query(String source, String destination) {

        String query = "SELECT rate FROM exchange_rate where SOURCE ='" + source + "' and DESTINATION ='" + destination + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if( cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex("rate"));
        } else {

            return Utilities.retrieveSavedData(source,destination);
        }
    }

    public int bulkInsert(Uri uri, ContentValues[] values){
       SQLiteDatabase database = getReadableDatabase();
        database.beginTransaction();
        long rowID;
        int returnCount = 0;
        try {
            for (ContentValues contentValues : values){
             rowID = database.insert(ExchangeRateContract.ExchangeRates.TABLE_NAME, null, contentValues);
                if (rowID != -1) {
                 returnCount++;
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        return returnCount;
    }

    public void deleteTable()
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(ExchangeRateContract.ExchangeRates.TABLE_NAME, null, null) ;
    }

    public int tableRows() {
        String query = "SELECT COUNT(*) FROM exchange_rate";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int rowNum = cursor.getInt(0);

        return rowNum;
    }

    public void updateTable(Uri uri, ContentValues[] values) {
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        for (ContentValues contentValues : values) {
            database.update(ExchangeRateContract.ExchangeRates.TABLE_NAME, contentValues, null, null);
        }
        database.endTransaction();
    }

}
