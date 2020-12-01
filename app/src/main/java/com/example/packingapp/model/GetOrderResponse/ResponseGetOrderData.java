package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetOrderData {

    @SerializedName("order_number")
    private String order_number;

    @SerializedName("OutBound_delivery")
    private String OutBound_delivery;

    @SerializedName("customer")
    private customer customer;

    @SerializedName("delivery")
    private delivery delivery;

    @SerializedName("picker_confirmation_time")
    private String picker_confirmation_time;

    @SerializedName("grand_total")
    private String grand_total;

    @SerializedName("currency")
    private String currency;

    @SerializedName("shipping_fees")
    private String shipping_fees;

    @SerializedName("time_slot")
    private String time_slot;

    @SerializedName("Out_From_Loc")
    private String Out_From_Loc;

    @SerializedName("items")
    private List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails;

    public String getOutBound_delivery() {
        return OutBound_delivery;
    }

    public void setOutBound_delivery(String outBound_delivery) {
        OutBound_delivery = outBound_delivery;
    }

    public String getPicker_confirmation_time() {
        return picker_confirmation_time;
    }

    public void setPicker_confirmation_time(String picker_confirmation_time) {
        this.picker_confirmation_time = picker_confirmation_time;
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

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    public delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(delivery delivery) {
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
