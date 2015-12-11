package app.com.example.grace.currencycalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private EditTextPreference settings_currency_no;
    private String minVal;
    private String maxVal;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        addPreferencesFromResource(R.xml.pref_general);
        createListener();
        settings_currency_no = (EditTextPreference) findPreference("no_of_currency");
        minVal = "10";
        maxVal = "30";
        bindPreferenceSummaryToValue(findPreference("no_of_currency"));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.
                getDefaultSharedPreferences(preference.getContext()).
                getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        String stringValue = value.toString();

        if(preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return  true;
    }

    private void createListener() {
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(
                    SharedPreferences sharedPreferences, String key) {
                String value = sharedPreferences.getString("no_of_currency", minVal);

                try {
                    if (Integer.parseInt(value) < Integer.parseInt(minVal)) {
                        settings_currency_no.setText(minVal);
                        Toast.makeText(getApplicationContext(), "Minimum allowed is " + R.string.min_currency_display, Toast.LENGTH_SHORT).show();
                    }

                   else if (Integer.parseInt(value) > Integer.parseInt(maxVal)) {
                        settings_currency_no.setText(maxVal);
                        Toast.makeText(getApplicationContext(), "Maximum allowed is " + R.string.max_currency_display, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NumberFormatException exception) {
                    Toast.makeText(getApplicationContext(), "Please, enter a valid number ", Toast.LENGTH_SHORT).show();
                }
            }
        };
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .registerOnSharedPreferenceChangeListener(listener);
    }

}
