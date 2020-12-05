package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ItemsOrderDataDBDetails")
public class ItemsOrderDataDBDetails {
    @PrimaryKey(autoGenerate = true)
    private int uid;

//    @ColumnInfo(name ="Order_number")
//    @SerializedName("Order_number")
//    private String Order_number;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

//    @ColumnInfo(name = "Name_of_item")
//    @SerializedName("Name_of_item")
//    private String Name_of_item;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    private String price;

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    private String quantity;


    @ColumnInfo(name = "sku")
    @SerializedName("sku")
    private String sku;





    @ColumnInfo(name = "unit_of_measurement")
    @SerializedName("unit_of_measurement")
    private String unite;

    @ColumnInfo(name = "TrackingNumber")
    @SerializedName("TrackingNumber")
    private String TrackingNumber;


    public ItemsOrderDataDBDetails(String name, String sku, String price, String quantity, String unite) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.unite = unite;
    }

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
