package com.zack.gojektestjava;

import android.app.Application;
import android.content.Context;

import com.zack.gojektestjava.database.RepoDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharePref.initialize(getApplicationContext());
        RepoDatabase.initialize(getApplicationContext());
    }
}
