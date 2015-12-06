package app.com.example.grace.currencycalculator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ExchangeRateDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "exchange.db";
    static final int DATABASE_VERSION = 2;

    Context context;
    public ExchangeRateDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ExchangeRateContract.ExchangeRates.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_RATES_TABLE = "CREATE TABLE "+ ExchangeRateContract.ExchangeRates.TABLE_NAME + " (" +
                ExchangeRateContract.ExchangeRates.COLUMN_SOURCE + " TEXT, "+
                ExchangeRateContract.ExchangeRates.COLUMN_DESTINATION + " TEXT, " +
                ExchangeRateContract.ExchangeRates.COLUMN_RATE + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_RATES_TABLE);
    }
}
