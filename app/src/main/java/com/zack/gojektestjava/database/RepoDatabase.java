package com.zack.gojektestjava.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RepoEntity.class}, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    private static RepoDatabase instance;

    public static synchronized void initialize(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RepoDatabase.class,
                    "gojek_test_db").build();
        }
    }

    public static synchronized RepoDatabase getInstance() {
        return instance;
    }

    public abstract RepoDao repoDao();
}
