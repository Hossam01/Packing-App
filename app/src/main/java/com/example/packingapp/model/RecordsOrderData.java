package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

public class RecordsOrderData {

    @SerializedName("Order_number")
    private String Order_number;

    @SerializedName("Customer_name")
    private String Customer_name;

    @SerializedName("Customer_phone")
    private String Customer_phone;

    @SerializedName("Customer_address_en")
    private String Customer_address_en;

    @SerializedName("Customer_address_ar")
    private String Customer_address_ar;

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

    public String getOrder_number() {
        return Order_number;
    }

    public void setOrder_number(String order_number) {
        Order_number = order_number;
    }

    public String getCustomer_name() {
        return Customer_name;
    }

    public void setCustomer_name(String customer_name) {
        Customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return Customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        Customer_phone = customer_phone;
    }

    public String getCustomer_address_en() {
        return Customer_address_en;
    }

    public void setCustomer_address_en(String customer_address_en) {
        Customer_address_en = customer_address_en;
    }

    public String getCustomer_address_ar() {
        return Customer_address_ar;
    }

    public void setCustomer_address_ar(String customer_address_ar) {
        Customer_address_ar = customer_address_ar;
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
}
