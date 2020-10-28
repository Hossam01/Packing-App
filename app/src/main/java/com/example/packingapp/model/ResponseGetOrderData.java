package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetOrderData {

    @SerializedName("records")
    private RecordsOrderData records;

    public void setRecords(RecordsOrderData records) {
        this.records = records;
    }

    public RecordsOrderData getRecords() {
        return records;
    }

}
