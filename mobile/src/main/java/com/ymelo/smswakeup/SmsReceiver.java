package com.ymelo.smswakeup;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
        Log.d(TAG, "Received SMS");
        if(!isScreenOn(context)) {
            Intent startIntent = new Intent(context, InvisibleActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startIntent);

        }
    }

    /**
     * Helper method retrieving the value of the 'screen on' state.
     * The internal method used depends on the OS level
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    private boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Deprecated as of KITKAT_WATCH (20)
            return pm.isScreenOn();
        } else {
            return pm.isInteractive();
        }
    }

    /**
     * Enables the reception of the SMS broadcast.
     * @param context
     */
    public static void enable(Context context) {
        Log.d(TAG, "Enabling SMS receiver");
        changeReceiverState(context, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    /**
     * Disables the reception of the SMS broadcast
     * @param context
     */
    public static void disable(Context context) {
        Log.d(TAG, "Disabling SMS receiver");
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
