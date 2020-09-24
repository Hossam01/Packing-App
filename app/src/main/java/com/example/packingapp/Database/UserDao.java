package com.example.packingapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.packingapp.model.RecordsItem;

import java.util.List;

import io.reactivex.Observable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Observable<List<RecordsItem>> getAll();

    @Insert(onConflict = REPLACE)
    void insertUser(RecordsItem mUser);
}
