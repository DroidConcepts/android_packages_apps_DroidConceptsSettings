package com.droidconcepts.settings.activities;

import com.droidconcepts.settings.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import java.io.File;

/**
 * Performance Settings
 */

public class PerformanceSettingsActivity extends PreferenceActivity {
    private static final String LOCK_HOME_PREF = "pref_lock_home";
    private static final int LOCK_HOME_DEFAULT = 0;
    private static final String LOCK_MMS_PREF = "pref_lock_mms";
    private static final int LOCK_MMS_DEFAULT = 1;

    private CheckBoxPreference mLockHomePref;
    private CheckBoxPreference mLockMmsPref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setTitle(R.string.performance_title);
        addPreferencesFromResource(R.xml.performance_settings);       
        PreferenceScreen prefSet = getPreferenceScreen(); 

        mLockHomePref = (CheckBoxPreference) prefSet.findPreference(LOCK_HOME_PREF);
        mLockHomePref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_HOME_IN_MEMORY, LOCK_HOME_DEFAULT) == 1);

        mLockMmsPref = (CheckBoxPreference) prefSet.findPreference(LOCK_MMS_PREF);
        mLockMmsPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCK_MMS_IN_MEMORY, LOCK_MMS_DEFAULT) == 1);
    }
    
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mLockHomePref) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_HOME_IN_MEMORY, mLockHomePref.isChecked() ? 1 : 0);
            return true;
        }
        if (preference == mLockMmsPref) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCK_MMS_IN_MEMORY, mLockMmsPref.isChecked() ? 1 : 0);
            return true;
        }

        return false;
    }

}
