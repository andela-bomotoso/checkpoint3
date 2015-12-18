package app.com.example.grace.currencycalculator.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.PortUnreachableException;

public class ExchangeRateContract {

    public static final String CONTENT_AUTHORITY = "app.com.example.grace.currencycalculator";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +CONTENT_AUTHORITY);
    public static final String PATH_EXCHANGE_RATE = "exchange_rate";

    public static final class ExchangeRates implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EXCHANGE_RATE).build();
        public static final String TABLE_NAME = "exchange_rate";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_DESTINATION = "destination";
        public static final String COLUMN_RATE = "rate";
    }

}
