package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TrackingnumbersListDB")
public class TrackingnumbersListDB {
    @PrimaryKey(autoGenerate = true)
    private int uid;



    @ColumnInfo(name = "TrackingNumber")
    @SerializedName("TrackingNumber")
    private String TrackingNumber;

    public TrackingnumbersListDB() {
    }

    public TrackingnumbersListDB(String trackingNumber) {
        TrackingNumber = trackingNumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTrackingNumber() {
        return TrackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        TrackingNumber = trackingNumber;
    }
}
