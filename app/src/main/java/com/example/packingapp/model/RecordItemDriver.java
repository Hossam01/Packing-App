package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

public class RecordItemDriver {
    @SerializedName("Driver_ID")
    private String driverID;
    @SerializedName("Status")
    private String status;
    @SerializedName("Company")
    private String company;
    @SerializedName("NameArabic")
    private String nameArabic;
    @SerializedName("Address")
    private String address;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("NameEnglish")
    private String nameEnglish;
    @SerializedName("Vechile_ID")
    private String vechileID;

    public String getDriverID(){
        return driverID;
    }

    public String getStatus(){
        return status;
    }

    public String getCompany(){
        return company;
    }

    public String getNameArabic(){
        return nameArabic;
    }

    public String getAddress(){
        return address;
    }

    public String getPhone(){
        return phone;
    }

    public String getNameEnglish(){
        return nameEnglish;
    }

    public String getVechileID(){
        return vechileID;
    }
}
