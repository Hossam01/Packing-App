package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

public class RecodsItemVehicle {
    @SerializedName("LienceNumber")
    private String lienceNumber;
    @SerializedName("NameArabic")
    private String nameArabic;
    @SerializedName("NameEnglish")
    private String nameEnglish;
    @SerializedName("Vechile_ID")
    private String vechileID;
    @SerializedName("Weight")
    private String weight;

    public void setLienceNumber(String lienceNumber){
        this.lienceNumber = lienceNumber;
    }

    public String getLienceNumber(){
        return lienceNumber;
    }

    public void setNameArabic(String nameArabic){
        this.nameArabic = nameArabic;
    }

    public String getNameArabic(){
        return nameArabic;
    }

    public void setNameEnglish(String nameEnglish){
        this.nameEnglish = nameEnglish;
    }

    public String getNameEnglish(){
        return nameEnglish;
    }

    public void setVechileID(String vechileID){
        this.vechileID = vechileID;
    }

    public String getVechileID(){
        return vechileID;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public String getWeight(){
        return weight;
    }
}
