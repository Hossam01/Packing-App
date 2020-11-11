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

    @ColumnInfo(name = "barcode")
    @SerializedName("barcode")
    private String barcode;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    private String price;

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    private String quantity;

    @ColumnInfo(name = "unite")
    @SerializedName("unite")
    private String unite;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getTrackingNumber() {
        return TrackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        TrackingNumber = trackingNumber;
    }
}
