package com.ymelo.SmsWakeUp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yohann on 18/11/14.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(!pm.isScreenOn()) {
            Intent startIntent = new Intent(context, InvisibleActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startIntent);
            Log.d(TAG, "Received SMS");
        }
    }

    public static void enable(Context context) {
        changeReceiverState(context, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    public static void disable(Context context) {
        changeReceiverState(context, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    private static void changeReceiverState(Context context, int state) {
        ComponentName receiver = new ComponentName(context, SmsReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                state,
                PackageManager.DONT_KILL_APP);
    }
}
