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

    @Singleton
    @Provides
    @Named("Database")
    public RepoDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                RepoDatabase.class,
                "gojek_test_db").build();
    }
}
