package com.example.packingapp.model.RecievePacked;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
        private String Tracking_Number;

    @ColumnInfo(name ="ZONE")
    @SerializedName("ZONE")
    private String Zone;


    public RecievePackedModule() {
    }

    public RecievePackedModule(String ORDER_NO, String NO_OF_PACKAGES,
                               String tracking_Number) {
        this.ORDER_NO = ORDER_NO;
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
        Tracking_Number = tracking_Number;
    }

    public RecievePackedModule(String ORDER_NO, String NO_OF_PACKAGES,
                               String tracking_Number,String Zone) {
        this.ORDER_NO = ORDER_NO;
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
        Tracking_Number = tracking_Number;
        this.Zone=Zone;
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

    public String getTracking_Number() {
        return Tracking_Number;
    }

    public void setTracking_Number(String tracking_Number) {
        Tracking_Number = tracking_Number;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }
}
