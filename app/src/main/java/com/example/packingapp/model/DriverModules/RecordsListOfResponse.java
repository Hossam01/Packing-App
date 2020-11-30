package com.example.packingapp.model.DriverModules;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;

public class RecordsListOfResponse {

    @ColumnInfo(name = "ORDER_NO")
    @SerializedName("ORDER_NO")
    private String orderNumber;

    @ColumnInfo(name = "CUSTOMER_PHONE")
    @SerializedName("CUSTOMER_PHONE")
    private String CUSTOMER_PHONE;

    public RecordsListOfResponse(String orderNumber, String CUSTOMER_PHONE) {
        this.orderNumber = orderNumber;
        this.CUSTOMER_PHONE = CUSTOMER_PHONE;
    }



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCUSTOMER_PHONE() {
        return CUSTOMER_PHONE;
    }

    public void setCUSTOMER_PHONE(String CUSTOMER_PHONE) {
        this.CUSTOMER_PHONE = CUSTOMER_PHONE;
    }

}
