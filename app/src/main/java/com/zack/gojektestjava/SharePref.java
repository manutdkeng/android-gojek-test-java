package com.zack.gojektestjava;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    private static final String SHARE_PREFERENCES_NAME = "TEST_SHARED_PREF";

    private static final String LAST_UPDATE_DATE = "lAST_UPDATED_DATE";

    private static SharePref instance;

    private SharedPreferences myPrefs;

    public static synchronized void initialize(Context ctx) {
        if (instance == null) {
            instance = new SharePref(ctx);
        }
    }

    public static synchronized SharePref getInstance() {
        return instance;
    }

    private SharePref(Context context) {
        myPrefs = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
    }

    public void setLastUpdatedDate(long timeInMillies) {
        SharedPreferences.Editor myPrefsEdit = myPrefs.edit();
        myPrefsEdit.putLong(LAST_UPDATE_DATE, timeInMillies);
        myPrefsEdit.apply();
    }

    public long getLastUpdatedDate(Context ctx) {
        return myPrefs.getLong(LAST_UPDATE_DATE, 0);
    }
}
