package com.example.packingapp.model.DriverModules;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;

public class DriverPackages_Respones_Details_recycler {


    @ColumnInfo(name = "records")
    @SerializedName("records")
    private List<DriverPackages_Details_DB> records;

    public DriverPackages_Respones_Details_recycler(List<DriverPackages_Details_DB> records) {
        this.records = records;
    }

    public List<DriverPackages_Details_DB> getRecords() {
        return records;
    }

    public void setRecords(List<DriverPackages_Details_DB> records) {
        this.records = records;
    }
}
