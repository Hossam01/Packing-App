package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

public class RecordItemWay {
    @SerializedName("Direction_ID")
    private String Direction_ID;
    @SerializedName("NameArabic")
    private String nameArabic;
    @SerializedName("NameEnglish")
    private String nameEnglish;
    @SerializedName("Status")
    private String Status;
    @SerializedName("Estimation_Time")
    private String Estimation_Time;
    @SerializedName("Stations")
    private String Stations;
    @SerializedName("Driver_ID")
    private String Driver_ID;

    public String getDirection_ID() {
        return Direction_ID;
    }

    public void setDirection_ID(String direction_ID) {
        Direction_ID = direction_ID;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEstimation_Time() {
        return Estimation_Time;
    }

    public void setEstimation_Time(String estimation_Time) {
        Estimation_Time = estimation_Time;
    }

    public String getStations() {
        return Stations;
    }

    public void setStations(String stations) {
        Stations = stations;
    }

    public String getDriver_ID() {
        return Driver_ID;
    }

    public void setDriver_ID(String driver_ID) {
        Driver_ID = driver_ID;
    }

    public String getVechile_ID() {
        return Vechile_ID;
    }

    public void setVechile_ID(String vechile_ID) {
        Vechile_ID = vechile_ID;
    }

    @SerializedName("Vechile_ID")
    private String Vechile_ID;
}
