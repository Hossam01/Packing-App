package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ItemsOrderDataDBDetails")
public class ItemsOrderDataDBDetails {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @SerializedName("Delivery_date")
    private String Delivery_date;

    @SerializedName("Time_slot")
    private String Time_slot;

    @SerializedName("Name_of_item")
    private String Name_of_item;

    @SerializedName("Quantity")
    private String Quantity;

    @SerializedName("Price")
    private String Price;

    @SerializedName("Barcode")
    private String Barcode;

    @SerializedName("TrackingNumber")
    private String TrackingNumber;

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDelivery_date() {
        return Delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        Delivery_date = delivery_date;
    }

    public String getTime_slot() {
        return Time_slot;
    }

    public void setTime_slot(String time_slot) {
        Time_slot = time_slot;
    }

    public String getName_of_item() {
        return Name_of_item;
    }

    public void setName_of_item(String name_of_item) {
        Name_of_item = name_of_item;
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

    public String getTrackingNumber() {
        return TrackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        TrackingNumber = trackingNumber;
    }
}
