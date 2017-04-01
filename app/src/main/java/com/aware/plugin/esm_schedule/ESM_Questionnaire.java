package com.aware.plugin.esm_schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

/**
 * Created by niels on 29/03/2017.
 */

public class ESM_Questionnaire extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!intent.getAction().equals("ESM_TRIGGERED")) {
            return;
        }

        Log.d("Niels", "Trigger received");
        
        buildESM(context);
    }

    private void buildESM(Context context) {
        try {
            ESMFactory factory = new ESMFactory();

            ESM_Radio radio_1 = new ESM_Radio();
            radio_1.addRadio("Option 1")
                    .addRadio("Option 2")
                    .addRadio("Option 3")
                    .addRadio("Option 4")
                    .setTitle("ESM Title")
                    .setTrigger("ESM Trigger")
                    .setSubmitButton("OK")
                    .setInstructions("");
            factory.addESM(radio_1);

            ESM_Freetext freetext_1 = new ESM_Freetext();
            freetext_1.setTitle("ESM Title")
                    .setTrigger("ESM Trigger")
                    .setSubmitButton("OK")
                    .setInstructions("ESM Freetext question");
            factory.addESM(freetext_1);

            //Queue them
            Intent queueESM = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
            queueESM.putExtra(ESM.EXTRA_ESM, factory.build());
            context.sendBroadcast(queueESM);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
