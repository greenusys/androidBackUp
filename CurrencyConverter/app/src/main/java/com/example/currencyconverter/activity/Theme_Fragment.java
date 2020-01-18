package com.example.currencyconverter.activity;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.currencyconverter.R;
import com.example.currencyconverter.widget.ThemeHelper;


public class Theme_Fragment extends PreferenceFragmentCompat {

    static final String TAG = "SettingsFragmentTag";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference themePreference = findPreference("themePref");
        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            String themeOption = (String) newValue;
                            ThemeHelper.applyTheme(themeOption);
                            return true;
                        }
                    });
        }
    }
}