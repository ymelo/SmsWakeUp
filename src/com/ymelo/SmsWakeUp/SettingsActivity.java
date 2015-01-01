package com.ymelo.SmsWakeUp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by yohann on 09/12/14.
 */
public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences.OnSharedPreferenceChangeListener listener = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                          String key) {
                        boolean activated = sharedPreferences.getBoolean(getString(R.string.pref_key_activated), false);
                        if(activated) {
                            SmsReceiver.enable(getApplicationContext());
                        } else {
                            SmsReceiver.disable(getApplicationContext());
                        }
                    }
                };

    }


}
