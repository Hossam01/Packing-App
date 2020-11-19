package com.example.packingapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.PackedPackageItemsModule;
import com.example.packingapp.model.PackedPackageModule;
import com.example.packingapp.model.RecordsItem;
import com.example.packingapp.model.TrackingnumbersListDB;

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

//    @Query(" INSERT INTO OrderDataModuleDBHeader VALUES(" +)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrderHeader(OrderDataModuleDBHeader orderDataModuleDBHeader);

    @Query("UPDATE OrderDataModuleDBHeader SET OutBound_delivery = :OutBoundDelivery WHERE  Order_number in (:Ordernumber) ")
    void UpdateOutBoundDelievery(String OutBoundDelivery ,String Ordernumber);

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertOrderItems(List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails);

    @Query("SELECT * FROM OrderDataModuleDBHeader")
    Observable<OrderDataModuleDBHeader> getHeader();

    @Query("SELECT * FROM OrderDataModuleDBHeader")
    OrderDataModuleDBHeader getHeaderToUpload();

    @Query("SELECT * FROM itemsOrderDataDBDetails")
    List<ItemsOrderDataDBDetails> getDetailsTrackingnumberToUpload();

    @Query("SELECT Order_number FROM OrderDataModuleDBHeader")
    String getOrderNumber();

    @Query("DELETE FROM OrderDataModuleDBHeader")
    void deleteAllHeader();

    @Query("DELETE FROM itemsOrderDataDBDetails")
    void deleteAllOrderItems();

    @Query("DELETE FROM TrackingnumbersListDB")
    void deleteAllTrckingNumber();

    @Query("SELECT * FROM itemsOrderDataDBDetails where TrackingNumber =:tracking")
    Observable<List<ItemsOrderDataDBDetails>> getAllItem(String tracking);

    @Query("SELECT * FROM itemsOrderDataDBDetails where TrackingNumber is null or TrackingNumber =''")
    List<ItemsOrderDataDBDetails> getAllItemsWithoutTrackingnumber();

    @Query("SELECT * FROM itemsOrderDataDBDetails where TrackingNumber is not null or TrackingNumber !=''")
    List<ItemsOrderDataDBDetails> CheckItemsWithTrackingnumber();

    @Delete
    void deleteOrder(OrderDataModuleDBHeader mUser);

    @Query("SELECT * FROM itemsOrderDataDBDetails where sku =:barcode")
    List<ItemsOrderDataDBDetails> getItem(String barcode);



    @Query("SELECT * FROM TrackingnumbersListDB ORDER BY TrackingNumber DESC LIMIT 1")
    TrackingnumbersListDB getLastTrackingnumber();

//    @Query("UPDATE itemsOrderDataDBDetails SET TrackingNumber =:tracking where (SELECT * FROM itemsOrderDataDBDetails ORDER BY TrackingNumber DESC LIMIT 1)")
//    ItemsOrderDataDBDetails updatetrackingnumber(String tracking);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insertrackingnumber(TrackingnumbersListDB trackingnumbersListDB);

    @Query("UPDATE itemsOrderDataDBDetails SET TrackingNumber = :tracking WHERE  sku in (:items) ")
     void updatetrackingnumberforListOfItems(String tracking , List<String> items);

    @Query("SELECT DISTINCT TrackingNumber FROM itemsOrderDataDBDetails where TrackingNumber is not null or TrackingNumber !=''")
    List<PackedPackageModule> getAllPckages();

    @Query("SELECT * FROM itemsOrderDataDBDetails where TrackingNumber =:TrackingNumber")
    List<PackedPackageItemsModule> getItemsOfTrackingNumber(String TrackingNumber);

    @Query("UPDATE itemsOrderDataDBDetails SET TrackingNumber = NULL WHERE  TrackingNumber in (:tracking) ")
    void DeleteTrackingNumber(String tracking );

    @Query("UPDATE TrackingnumbersListDB SET TrackingNumber = NULL WHERE  TrackingNumber in (:tracking) ")
    void DeleteTrackingNumberFromtrackingtable(String tracking );

    @Query("UPDATE itemsOrderDataDBDetails SET TrackingNumber = NULL WHERE  sku in (:Barcode) ")
    void DeleteTrackingNumberForItem(String Barcode );
}
