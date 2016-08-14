package com.ymelo.smswakeup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Invisible activity, whose only purpose is to allow the app to turn the screen on.
 * For this to happen, the activity is put on top (this app becomes the running app).
 */
public class InvisibleActivity extends Activity {
    private static final String TAG = "InvisibleActivity";
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenTurnOn();
        //Finish the activity in a handler (unfortunately we need to
        //wait a little bit before finishing, so the screen can be turned
        //on correctly
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 110);

    }

    /**
     * Helper method that turns the screen on
     */
    public void screenTurnOn() {
        Log.d(TAG, "Turning screen on");
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}
