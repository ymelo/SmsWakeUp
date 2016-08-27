package com.ymelo.smswakeup;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
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
        private static final int PERMISSION_REQUEST_WAKE_LOCK = 1;
//        private static final int PERMISSION_REQUEST_READ_SMS = 10;
//        private static final int PERMISSION_REQUEST_RECEIVE_SMS = 100;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ArrayList<String> permissionsRequested = new ArrayList<String>();
            int wakePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WAKE_LOCK);

            if(wakePermission != PackageManager.PERMISSION_GRANTED) {
                permissionsRequested.add(Manifest.permission.WAKE_LOCK);
            }
//            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//                permissionsRequested.add(Manifest.permission.READ_SMS);
//            }
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                permissionsRequested.add(Manifest.permission.RECEIVE_SMS);
            }
            if(permissionsRequested.size() > 0) {
                String[] permissions = permissionsRequested.toArray(new String[permissionsRequested.size()]);
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        PERMISSION_REQUEST_WAKE_LOCK);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode == PERMISSION_REQUEST_WAKE_LOCK && grantResults.length > 0) {
                boolean success = true;
                for (int result: grantResults) {
                    if(result != PackageManager.PERMISSION_GRANTED) {
                        //Woot
                    } else {
                        //Permission refused...

                    }
                }
            } else {
                //failure?
            }
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

