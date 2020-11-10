package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;

public class ResponseGetOrderData {

    @SerializedName("order_number")
    private String order_number;

    @SerializedName("customer")
    private customer customer;

    @SerializedName("delivery")
    private delivery delivery;

    @SerializedName("grand_total")
    private String grand_total;

    @SerializedName("currency")
    private String currency;

    @SerializedName("time_slot")
    private String time_slot;


    @SerializedName("items")
    private List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails;



//    @SerializedName("Out_From_Loc")
//    private String Out_From_Loc;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public com.example.packingapp.model.GetOrderResponse.customer getCustomer() {
        return customer;
    }

    public void setCustomer(com.example.packingapp.model.GetOrderResponse.customer customer) {
        this.customer = customer;
    }

    public com.example.packingapp.model.GetOrderResponse.delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(com.example.packingapp.model.GetOrderResponse.delivery delivery) {
        this.delivery = delivery;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public List<ItemsOrderDataDBDetails> getItemsOrderDataDBDetails() {
        return itemsOrderDataDBDetails;
    }

    public void setItemsOrderDataDBDetails(List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails) {
        this.itemsOrderDataDBDetails = itemsOrderDataDBDetails;
    }
}
