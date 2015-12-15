package app.com.example.grace.currencycalculator.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class ExchangeRateDbHelperTest extends AndroidTestCase {

    ExchangeRateDbHelper exchangeRateDbHelper;

    public ExchangeRateDbHelperTest() {

    }

    public ExchangeRateDbHelperTest(Context context) {
        exchangeRateDbHelper = new ExchangeRateDbHelper(context);
    }

    void deleteTheDatabase() {
        mContext.deleteDatabase(ExchangeRateDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testOnCreate() throws Exception {

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(ExchangeRateContract.ExchangeRates.TABLE_NAME);

        SQLiteDatabase db = new ExchangeRateDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: Database has not been created correctly",
                c.moveToFirst());

                do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Database was created without the exchange_rate table",
                tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + ExchangeRateContract.ExchangeRates.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        final HashSet<String> ExchangeColumnHashSet = new HashSet<String>();
        ExchangeColumnHashSet.add(ExchangeRateContract.ExchangeRates.COLUMN_SOURCE);
        ExchangeColumnHashSet.add(ExchangeRateContract.ExchangeRates.COLUMN_DESTINATION);
        ExchangeColumnHashSet.add(ExchangeRateContract.ExchangeRates.COLUMN_RATE);

        int columnNameIndex = c.getColumnIndex("name");

        do {
            String columnName = c.getString(columnNameIndex);
            ExchangeColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                ExchangeColumnHashSet.isEmpty());
        db.close();
    }

}