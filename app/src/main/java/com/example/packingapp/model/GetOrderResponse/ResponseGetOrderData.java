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

    @SerializedName("picker_confirmation_time")
    private String picker_confirmation_time;

    @SerializedName("customer_code")
    private String customer_code;

    @SerializedName("shipping_fees")
    private String shipping_fees;

    @SerializedName("currency")
    private String currency;

    @SerializedName("time_slot")
    private String time_slot;

    @SerializedName("Out_From_Loc")
    private String Out_From_Loc;

    @SerializedName("items")
    private List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails;

    public String getPicker_confirmation_time() {
        return picker_confirmation_time;
    }

    public void setPicker_confirmation_time(String picker_confirmation_time) {
        this.picker_confirmation_time = picker_confirmation_time;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getShipping_fees() {
        return shipping_fees;
    }

    public void setShipping_fees(String shipping_fees) {
        this.shipping_fees = shipping_fees;
    }

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

    public String getOut_From_Loc() {
        return Out_From_Loc;
    }

    public void setOut_From_Loc(String out_From_Loc) {
        Out_From_Loc = out_From_Loc;
    }

    public List<ItemsOrderDataDBDetails> getItemsOrderDataDBDetails() {
        return itemsOrderDataDBDetails;
    }

    public void setItemsOrderDataDBDetails(List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails) {
        this.itemsOrderDataDBDetails = itemsOrderDataDBDetails;
    }

}
