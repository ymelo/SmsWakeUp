package com.ymelo.SmsWakeUp;

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

    }
}
