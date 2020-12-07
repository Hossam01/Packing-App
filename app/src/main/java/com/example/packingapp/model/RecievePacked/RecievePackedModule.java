package com.example.packingapp.model.RecievePacked;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "RecievePackedModule")
public class RecievePackedModule {

        @PrimaryKey(autoGenerate = true)
        private int uid;

        @ColumnInfo(name ="ORDER_NO")
        @SerializedName("ORDER_NO")
        private String ORDER_NO;

        @ColumnInfo(name ="NO_OF_PACKAGES")
        @SerializedName("NO_OF_PACKAGES")
        private String NO_OF_PACKAGES;

        @ColumnInfo(name ="STATUS")
        @SerializedName("STATUS")
        private String STATUS;

        @ColumnInfo(name ="TRACKING_NO")
        @SerializedName("TRACKING_NO")
        private String TRACKING_NO;

        @ColumnInfo(name ="Zone")
        @SerializedName("Zone")
        private String Zone;

       @ColumnInfo(name ="CUSTOMER_NAME")
       @SerializedName("CUSTOMER_NAME")
       private String CUSTOMER_NAME;

       @ColumnInfo(name ="ADDRESS_CITY")
       @SerializedName("ADDRESS_CITY")
       private String ADDRESS_CITY;

       @ColumnInfo(name ="ITEM_PRICE")
       @SerializedName("ITEM_PRICE")
       private String ITEM_PRICE;

       @ColumnInfo(name ="OUTBOUND_DELIVERY")
       @SerializedName("OUTBOUND_DELIVERY")
       private String OUTBOUND_DELIVERY;

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getADDRESS_CITY() {
        return ADDRESS_CITY;
    }

    public void setADDRESS_CITY(String ADDRESS_CITY) {
        this.ADDRESS_CITY = ADDRESS_CITY;
    }

    public String getITEM_PRICE() {
        return ITEM_PRICE;
    }

    public void setITEM_PRICE(String ITEM_PRICE) {
        this.ITEM_PRICE = ITEM_PRICE;
    }

    public String getOUTBOUND_DELIVERY() {
        return OUTBOUND_DELIVERY;
    }

    public void setOUTBOUND_DELIVERY(String OUTBOUND_DELIVERY) {
        this.OUTBOUND_DELIVERY = OUTBOUND_DELIVERY;
    }

    public RecievePackedModule() {
    }



    public RecievePackedModule(String ORDER_NO, String NO_OF_PACKAGES,
                               String tracking_Number) {
        this.ORDER_NO = ORDER_NO;
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
        TRACKING_NO = tracking_Number;
    }

    public RecievePackedModule(String ORDER_NO, String NO_OF_PACKAGES,
                               String tracking_Number,String Zone,String CUSTOMER_NAME,String ADDRESS_CITY,String ITEM_PRICE,String OUTBOUND_DELIVERY) {
        this.ORDER_NO = ORDER_NO;
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
        TRACKING_NO = tracking_Number;
        this.Zone=Zone;
        this.CUSTOMER_NAME=CUSTOMER_NAME;
        this.ADDRESS_CITY=ADDRESS_CITY;
        this.ITEM_PRICE=ITEM_PRICE;
        this.OUTBOUND_DELIVERY=OUTBOUND_DELIVERY;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public void setORDER_NO(String ORDER_NO) {
        this.ORDER_NO = ORDER_NO;
    }

    public String getNO_OF_PACKAGES() {
        return NO_OF_PACKAGES;
    }

    public void setNO_OF_PACKAGES(String NO_OF_PACKAGES) {
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
    }

    public String getTRACKING_NO() {
        return TRACKING_NO;
    }

    public void setTRACKING_NO(String TRACKING_NO) {
        this.TRACKING_NO = TRACKING_NO;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }
}
