package com.zack.gojektestjava;

import android.app.Application;

import com.zack.gojektestjava.database.RepoDatabase;
import com.zack.gojektestjava.util.SharePref;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharePref.initialize(getApplicationContext());
        RepoDatabase.initialize(getApplicationContext());
    }
}
