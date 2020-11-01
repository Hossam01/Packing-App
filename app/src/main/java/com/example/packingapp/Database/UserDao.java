package com.example.packingapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.packingapp.model.OrderDataModuleDB;
import com.example.packingapp.model.RecordsItem;
import com.example.packingapp.model.RecordsOrderData;

import java.util.List;

import io.reactivex.Observable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
   @Query("SELECT * FROM user")
   Observable<List<RecordsItem>> getAll();
 
    @Insert(onConflict = REPLACE)
    void insertUser(RecordsItem mUser);


    @Delete
    void delete(RecordsItem mUser);

    @Update
    void updateUser(RecordsItem mUser);

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insertOrder(OrderDataModuleDB recordsOrderData);

}
