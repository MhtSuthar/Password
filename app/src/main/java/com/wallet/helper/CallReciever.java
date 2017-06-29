package com.wallet.helper;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.wallet.util.AppUtils;
import com.wallet.util.SharedPrefUtil;

/**
 * Created by AND004 on 27-May-17.
 */

public class CallReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        String pin = SharedPrefUtil.getString(AppUtils.PIN, "") + "#";
        if (number.equalsIgnoreCase(pin)) {
            PackageManager p = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, "com.wallet.activity.Splashscreen");
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            Toast.makeText(context, "Master Password Unhide", Toast.LENGTH_SHORT).show();
        }
//            Toast.makeText(context, "Outgoing: " + number, Toast.LENGTH_LONG).show();

     /*   PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, "com.wallet.activity.Splashscreen");
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(context, "Master Password Unhide", Toast.LENGTH_SHORT).show();

//        Toast.makeText(context, "reciever", Toast.LENGTH_SHORT).show();
        if (intent.getAction() == Intent.ACTION_NEW_OUTGOING_CALL) {
            Toast.makeText(context, "outgoing call detected:\n" + intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER), Toast.LENGTH_LONG);
        }

        if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Toast.makeText(context, "unhide app", Toast.LENGTH_SHORT).show();

            PackageManager p1 = context.getPackageManager();
            ComponentName componentName1 = new ComponentName(context, "com.wallet.activity.Splashscreen");
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }*/
    }
}
