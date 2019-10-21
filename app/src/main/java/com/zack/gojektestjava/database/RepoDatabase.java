package com.zack.gojektestjava.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RepoEntity.class}, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    public abstract RepoDao repoDao();
}
