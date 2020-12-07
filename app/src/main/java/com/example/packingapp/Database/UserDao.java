package com.example.packingapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.packingapp.model.DriverModules.DriverPackages_Details_DB;
import com.example.packingapp.model.DriverModules.DriverPackages_Header_DB;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.PackedPackageItemsModule;
import com.example.packingapp.model.PackedPackageModule;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.RecordsItem;
import com.example.packingapp.model.TrackingnumbersListDB;

import java.util.List;

import io.reactivex.Observable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
   @Query("SELECT * FROM user")
   Observable<List<RecordsItem>> getUserData();

    @Query("SELECT * FROM user")
    RecordsItem getUserData_MU();


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

    @Query("SELECT count(TrackingNumber) NO_OF_PACKAGES FROM itemsOrderDataDBDetails where TrackingNumber LIKE :ORDER_NO ")
    String getNoOfPackagesToUpload(String ORDER_NO);

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



    @Query("SELECT * FROM TrackingnumbersListDB where TrackingNumber is not null ORDER BY TrackingNumber DESC LIMIT 1")
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

    @Insert(onConflict = REPLACE)
    void insertRecievePacked(RecievePackedModule recievePackedModule);

    @Query("SELECT * FROM RecievePackedModule where ORDER_NO =:ORDER_NO")
    List<RecievePackedModule> getRecievePacked_ORDER_NO(String ORDER_NO);

    @Query("SELECT * FROM RecievePackedModule where TRACKING_NO =:Tracking_Number")
    List<RecievePackedModule> getRecievePacked_Tracking_Number(String Tracking_Number);

    @Query("SELECT DISTINCT( ORDER_NO ) ,NO_OF_PACKAGES  , uid FROM RecievePackedModule ")
    List<RecievePackedModule> getRecievePacked_ORDER_NO_Distinct();

    //NO_OF_PACKAGES THAT RETURN FROM THIS QUERY IDECATE TO COUNT OF NO OF PACKAGES FOR ORDERNUMBER
    @Query("SELECT count(TRACKING_NO) NO_OF_PACKAGES FROM RecievePackedModule where ORDER_NO =:ORDER_NO")
    List<String> getRecievePacked_Tracking_Number_count(String ORDER_NO);

    @Query("DELETE FROM RecievePackedModule")
    void deleteRecievePackedModule();

    @Query("UPDATE RecievePackedModule SET Zone = :zone WHERE  ORDER_NO in (:ORDER_NO) ")
    void UpdatezoneForORDER_NO(String ORDER_NO , String zone );

    @Query("SELECT * FROM RecievePackedModule")
    List<RecievePackedModule> getorderNORecievePackedModule();

    @Insert(onConflict = REPLACE)
    void insertDriverOrders(List<DriverPackages_Header_DB> driverPackages_Header_dblist);

    @Insert(onConflict = REPLACE)
    void insertDriverPackages(List<DriverPackages_Details_DB> driverPackages_details_dbs);

    @Query("SELECT * FROM DriverPackages_Header_DB")
    DriverPackages_Header_DB getDriverorder();

    @Query("UPDATE DriverPackages_Header_DB SET Passcode =:Passcode WHERE  ORDER_NO =:OrderNumber ")
    void UpdatePasscode(String OrderNumber ,String Passcode);

    @Query("SELECT Passcode FROM DriverPackages_Header_DB where ORDER_NO =:ORDER_NO")
    String getPasscode(String ORDER_NO);

    @Query("DELETE FROM DriverPackages_Header_DB")
    void deleteDriverPackages_Header_DB();
    @Query("DELETE FROM DriverPackages_Details_DB")
    void deleteDriverPackages_Details_DB();

    @Query("UPDATE DriverPackages_Details_DB SET STATUS =:STATUS ,  REASON =:REASON WHERE  TRACKING_NO =:TrackingNumber ")
    void UpdatestatusAndReason_ForTrackingnumber(String TrackingNumber , String STATUS , String REASON);

    @Query("UPDATE DriverPackages_Details_DB SET STATUS =:STATUS WHERE  ORDER_NO =:OrderNumber And REASON is null or REASON =''")
    void UpdatestatusForNotRejectWhenClickConfirmed(String OrderNumber ,String STATUS );

    @Query("SELECT * FROM DriverPackages_Details_DB where REASON is not null or REASON !=''")
    List<DriverPackages_Details_DB> getAllPckagesForReject();

    @Query("SELECT * FROM DriverPackages_Details_DB WHERE  ORDER_NO =:OrderNumber")
    List<DriverPackages_Details_DB> getAllPckagesForUpload(String OrderNumber);

    @Query("SELECT * FROM RecievePackedModule")
    List<RecievePackedModule> getRecievePackedModule();

    @Query("SELECT * FROM OrderDataModuleDBHeader where Order_number =:OrderNumber")
    OrderDataModuleDBHeader getHeaderToUpload(String OrderNumber);

    @Query("SELECT * FROM TrackingnumbersListDB")
    List<TrackingnumbersListDB> countShipment();

}
