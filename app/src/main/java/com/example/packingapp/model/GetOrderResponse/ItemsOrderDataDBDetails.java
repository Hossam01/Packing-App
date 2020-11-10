package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ItemsOrderDataDBDetails")
public class ItemsOrderDataDBDetails {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

//    @ColumnInfo(name = "Name_of_item")
//    @SerializedName("Name_of_item")
//    private String Name_of_item;

    @ColumnInfo(name = "Barcode")
    @SerializedName("Barcode")
    private String Barcode;

    @ColumnInfo(name = "Price")
    @SerializedName("Price")
    private String Price;

    @ColumnInfo(name = "Quantity")
    @SerializedName("Quantity")
    private String Quantity;

    @ColumnInfo(name = "unite")
    @SerializedName("unite")
    private String unite_of_quantity;

    @ColumnInfo(name = "TrackingNumber")
    @SerializedName("TrackingNumber")
    private String TrackingNumber;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getUnite_of_quantity() {
        return unite_of_quantity;
    }

    public void setUnite_of_quantity(String unite_of_quantity) {
        this.unite_of_quantity = unite_of_quantity;
    }

    public String getTrackingNumber() {
        return TrackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        TrackingNumber = trackingNumber;
    }
}
