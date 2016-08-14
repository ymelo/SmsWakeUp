package com.ymelo.smswakeup;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import java.util.Timer;


/**
 * Created by yohann on 09/12/14.
 */
public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SmsPreferenceFragment()).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SmsPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String s) {
            new Runnable() {

                @Override
                public void run() {
                    Context context = getActivity();
                    if(context != null) {
                        boolean activated = sharedPreferences.getBoolean(getString(R.string.pref_key_activated), false);
                        if(activated) {
                            SmsReceiver.enable(context);
                            if(BuildConfig.DEBUG && false) {
                                Timer timer = new Timer();
                                timer.schedule(new TestTimerTask(getActivity()), 5000);
                            }
                        } else {
                            SmsReceiver.disable(context);
                        }
                    }
                }
            }.run();
        }
    }
}

