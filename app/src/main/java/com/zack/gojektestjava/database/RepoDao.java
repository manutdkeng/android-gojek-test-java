package com.zack.gojektestjava.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.zack.gojektestjava.SharePref;

import java.util.List;

@Dao
public abstract class RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void save(List<RepoEntity> entities);

    @Query("DELETE FROM repo_table")
    public abstract void deleteAll();

    @Query("SELECT * FROM repo_table")
    public abstract LiveData<List<RepoEntity>> getAll();

    @Query("SELECT * FROM repo_table LIMIT 1")
    public abstract RepoEntity getOne();

    @Transaction
    public void updateData(List<RepoEntity> entities) {
        deleteAll();
        if (entities != null) {
            save(entities);
            SharePref.getInstance().setLastUpdatedDate(System.currentTimeMillis());
        }
    }
}
