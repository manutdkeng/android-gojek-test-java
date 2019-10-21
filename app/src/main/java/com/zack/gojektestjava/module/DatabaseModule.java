package com.zack.gojektestjava.module;

import android.content.Context;

import androidx.room.Room;

import com.zack.gojektestjava.database.RepoDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private final Context mContext;

    public DatabaseModule(Context mContext) {
        this.mContext = mContext;
    }

    @Singleton
    @Provides
    @Named("Database")
    public RepoDatabase provideDatabase() {
        return Room.databaseBuilder(
                mContext.getApplicationContext(),
                RepoDatabase.class,
                "gojek_test_db").build();
    }
}
