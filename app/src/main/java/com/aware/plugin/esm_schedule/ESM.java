package com.aware.plugin.esm_schedule;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ui.PermissionsHandler;
import com.aware.utils.Scheduler;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by niels on 29/03/2017.
 */

public class ESM extends AppCompatActivity {

    private ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent aware = new Intent(this, Aware.class);
        startService(aware);

        boolean permissions_ok = true;
        for (String p : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                permissions_ok = false;
                break;
            }
        }

        if (!permissions_ok) {
            Intent permissions = new Intent(this, PermissionsHandler.class);
            permissions.putExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
            permissions.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            permissions.putExtra(PermissionsHandler.EXTRA_REDIRECT_ACTIVITY,
                    getPackageName() + "/" + Plugin.class.getName());

            startActivity(permissions);
            finish();
        } else {
            Applications.isAccessibilityServiceActive(getApplicationContext());

            Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
            Aware.startPlugin(getApplicationContext(), "com.aware.plugin.esm_schedule");
            setSchedule();
            Toast.makeText(getApplicationContext(), "Starting schedule", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSchedule() {
        // 5 minute schedule
        try{
            Scheduler.Schedule schedule_esm = new Scheduler.Schedule("schedule_esm");
            schedule_esm.setInterval(5)
                    .setActionType(Scheduler.ACTION_TYPE_BROADCAST)
                    .setActionIntentAction("ESM_TRIGGERED");
            Scheduler.saveSchedule(this, schedule_esm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
