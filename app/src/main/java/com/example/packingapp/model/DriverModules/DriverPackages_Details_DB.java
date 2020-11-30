package com.example.packingapp.model.DriverModules;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DriverPackages_Details_DB")
public class DriverPackages_Details_DB {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name ="ORDER_NO")
   @SerializedName("ORDER_NO")
   private String ORDER_NO;

    @ColumnInfo(name = "TRACKING_NO")
    @SerializedName("TRACKING_NO")
    private String TRACKING_NO;

    @ColumnInfo(name = "PACKAGE_PRICE")
    @SerializedName("PACKAGE_PRICE")
    private String PACKAGE_PRICE;

    //TODO this for ITEM_QUANTITY
    @ColumnInfo(name = "COUNT_ITEMS_PACKAGE")
    @SerializedName("COUNT_ITEMS_PACKAGE")
    private String COUNT_ITEMS_PACKAGE;

    @ColumnInfo(name = "STATUS")
    @SerializedName("STATUS")
    private String STATUS;

    @ColumnInfo(name = "REASON")
    @SerializedName("REASON")
    private String REASON;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public void setORDER_NO(String ORDER_NO) {
        this.ORDER_NO = ORDER_NO;
    }

    public String getTRACKING_NO() {
        return TRACKING_NO;
    }

    public void setTRACKING_NO(String TRACKING_NO) {
        this.TRACKING_NO = TRACKING_NO;
    }

    public String getPACKAGE_PRICE() {
        return PACKAGE_PRICE;
    }

    public void setPACKAGE_PRICE(String PACKAGE_PRICE) {
        this.PACKAGE_PRICE = PACKAGE_PRICE;
    }

    public String getCOUNT_ITEMS_PACKAGE() {
        return COUNT_ITEMS_PACKAGE;
    }

    public void setCOUNT_ITEMS_PACKAGE(String COUNT_ITEMS_PACKAGE) {
        this.COUNT_ITEMS_PACKAGE = COUNT_ITEMS_PACKAGE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }
}
