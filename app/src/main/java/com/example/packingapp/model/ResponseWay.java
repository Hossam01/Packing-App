package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWay {
    @SerializedName("records")
    private List<RecordItemWay> records;

    public void setRecords(List<RecordItemWay> records){
        this.records = records;
    }

    public List<RecordItemWay> getRecords(){
        return records;
    }
}
