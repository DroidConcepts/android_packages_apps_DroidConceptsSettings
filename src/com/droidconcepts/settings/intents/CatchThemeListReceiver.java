package com.droidconcepts.settings.intents;

import com.droidconcepts.settings.R;
import com.droidconcepts.settings.activities.ExtraTweaks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CatchThemeListReceiver extends BroadcastReceiver {

    public static final String catchList = "com.droidconcepts.settings.helper.GET_THEME_LIST";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(catchList)) {
            Bundle extras = intent.getExtras();
            int success = extras.getInt("gotfile");
            if (success == 1) { 
                String sdList[] = extras.getStringArray("filelist");
                ExtraTweaks.buildFileList(sdList, true);
            } else {
                ExtraTweaks.buildFileList(null, false);
            }
        }
    }
}
