package com.ymelo.SmsWakeUp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class InvisibleActivity extends Activity {
    private static final String TAG = "InvisibleActivity";
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenTurnOn();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 110);

    }

    public void screenTurnOn() {
        Log.d(TAG, "Turning screen on");
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}
