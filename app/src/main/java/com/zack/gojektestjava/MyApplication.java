package com.zack.gojektestjava;

import android.app.Application;

import com.zack.gojektestjava.database.RepoDatabase;
import com.zack.gojektestjava.module.ApplicationComponent;
import com.zack.gojektestjava.module.ContextModule;
import com.zack.gojektestjava.module.DaggerApplicationComponent;
import com.zack.gojektestjava.module.DatabaseModule;
import com.zack.gojektestjava.util.SharePref;

public class MyApplication extends Application {
    public ApplicationComponent component;

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        SharePref.initialize(getApplicationContext());
//        RepoDatabase.initialize(getApplicationContext());

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
