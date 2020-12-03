package com.example.packingapp.model.DriverModules;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;

public class ResponeEndOfDay {
    @ColumnInfo(name = "records")
    @SerializedName("records")
    private List<EndOfDayModule> EndOfDayModule;

    public ResponeEndOfDay(List<com.example.packingapp.model.DriverModules.EndOfDayModule> endOfDayModule) {
        EndOfDayModule = endOfDayModule;
    }

    public List<com.example.packingapp.model.DriverModules.EndOfDayModule> getEndOfDayModule() {
        return EndOfDayModule;
    }

    public void setEndOfDayModule(List<com.example.packingapp.model.DriverModules.EndOfDayModule> endOfDayModule) {
        EndOfDayModule = endOfDayModule;
    }
}
