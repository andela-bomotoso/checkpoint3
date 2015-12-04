package app.com.example.grace.currencycalculator.Controller;

import android.preference.EditTextPreference;
import android.text.TextWatcher;

/**
 * Created by GRACE on 12/3/2015.
 */
public abstract class MinMaxTextWatcher implements EditTextPreference.OnPreferenceChangeListener {
    int min, max;
    public MinMaxTextWatcher(int min, int max) {
        super();

        this.min = min;
        this.max = max;
    }

}
