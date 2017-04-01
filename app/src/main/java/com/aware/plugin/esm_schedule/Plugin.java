package com.aware.plugin.esm_schedule;

import android.content.Intent;
import android.util.Log;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;

public class Plugin extends Aware_Plugin {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Niels", "onCreate called");

        //Activate plugin -- do this ALWAYS as the last thing (this will restart your own plugin and apply the settings)
        Aware.startPlugin(this, "com.aware.plugin.esm_schedule");
    }

    //This function gets called every 5 minutes by AWARE to make sure this plugin is still running.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (PERMISSIONS_OK) {
            //Check if the user has toggled the debug messages
            DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

            //Initialize our plugin's settings
            Aware.setSetting(this, Settings.STATUS_ESM_SCHEDULE_EXAMPLE, true);

            Aware.startPlugin(this, "com.aware.plugin.esm_schedule");
            Aware.startAWARE(this);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Stop AWARE
        Aware.stopAWARE(this);
    }
}