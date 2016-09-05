package com.ymelo.smswakeup;

import android.content.Context;
import android.content.Intent;

import java.util.TimerTask;

/**
 * Created by Yohann on 2016-08-14.
 * Lazy test case.
 * Can also use adb shell am broadcast -a android.provider.Telephony.SMS_RECEIVED
 * instead of using this class
 */
public class TestTimerTask extends TimerTask{
    private Context mContext;

    public TestTimerTask(Context c) {
        mContext = c;
    }
    @Override
    public void run() {
        Intent intent = new Intent();
        intent.setAction("android.provider.Telephony.SMS_RECEIVED");
//        mContext.sendBroadcast(intent);
        SmsReceiver rec = new SmsReceiver();
        rec.onReceive(mContext, intent);

    }
}
