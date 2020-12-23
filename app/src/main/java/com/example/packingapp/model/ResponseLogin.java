package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLogin {
    @SerializedName("records")
    private List<RecordsItem> records;

    @SerializedName("Modules")
    private List<ModulesIDS> ModulesIDS;

    public void setRecords(List<RecordsItem> records) {
        this.records = records;
    }

    public List<RecordsItem> getRecords() {
        return records;
    }

    public List<com.example.packingapp.model.ModulesIDS> getModulesIDS() {
        return ModulesIDS;
    }

    public void setModulesIDS(List<com.example.packingapp.model.ModulesIDS> modulesIDS) {
        ModulesIDS = modulesIDS;
    }
}