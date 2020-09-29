package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLogin {
    @SerializedName("records")
    private List<RecordsItem> records;

    public void setRecords(List<RecordsItem> records) {
        this.records = records;
    }

    public List<RecordsItem> getRecords() {
        return records;
    }
}