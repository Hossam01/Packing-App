package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseVehicle {
    @SerializedName("records")
    private List<RecodsItemVehicle> records;

    public List<RecodsItemVehicle> getRecords() {
        return records;
    }

    public void setRecords(List<RecodsItemVehicle> records) {
        this.records = records;
    }
}