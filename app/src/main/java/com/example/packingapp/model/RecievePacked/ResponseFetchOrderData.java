package com.example.packingapp.model.RecievePacked;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;

public class ResponseFetchOrderData {

    @ColumnInfo(name ="records")
    @SerializedName("records")
    private List<RecievePackedModule> records;


    public ResponseFetchOrderData(List<RecievePackedModule> records) {
        this.records = records;
    }

    public List<RecievePackedModule> getRecords() {
        return records;
    }

    public void setRecords(List<RecievePackedModule> records) {
        this.records = records;
    }
}
