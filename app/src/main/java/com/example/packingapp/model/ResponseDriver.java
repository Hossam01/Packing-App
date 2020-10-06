package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDriver {
    @SerializedName("records")
    private List<RecordItemDriver> records;

    public List<RecordItemDriver> getRecords() {
        return records;
    }

    public void setRecords(List<RecordItemDriver> records) {
        this.records = records;
    }
}