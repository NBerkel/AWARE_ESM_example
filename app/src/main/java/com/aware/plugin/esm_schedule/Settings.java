package com.aware.plugin.esm_schedule;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.aware.Aware;

/**
 * Created by niels on 29/03/2017.
 */

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //Plugin settings in XML @xml/preferences
    public static final String STATUS_ESM_SCHEDULE_EXAMPLE = "status_esm_schedule_example";

    //Plugin settings UI elements
    private static CheckBoxPreference status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        status = (CheckBoxPreference) findPreference(STATUS_ESM_SCHEDULE_EXAMPLE);
        if (Aware.getSetting(this, STATUS_ESM_SCHEDULE_EXAMPLE).length() == 0) {
            Aware.setSetting(this, STATUS_ESM_SCHEDULE_EXAMPLE, true); //by default, the setting is true on install
        }
        status.setChecked(Aware.getSetting(getApplicationContext(), STATUS_ESM_SCHEDULE_EXAMPLE).equals("true"));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference setting = findPreference(key);
        if (setting.getKey().equals(STATUS_ESM_SCHEDULE_EXAMPLE)) {
            Aware.setSetting(this, key, sharedPreferences.getBoolean(key, false));
            status.setChecked(sharedPreferences.getBoolean(key, false));
        }
        if (Aware.getSetting(this, STATUS_ESM_SCHEDULE_EXAMPLE).equals("true")) {
            Aware.startPlugin(getApplicationContext(), "com.aware.plugin.esm_schedule");
        } else {
            Aware.stopPlugin(getApplicationContext(), "com.aware.plugin.esm_schedule");
        }
    }
}
