package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

public class customer {

    @SerializedName("name")
    private String name;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("customer_code")
    private String customer_code;

    @SerializedName("address")
    private address address;



    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public com.example.packingapp.model.GetOrderResponse.address getAddress() {
        return address;
    }

    public void setAddress(com.example.packingapp.model.GetOrderResponse.address address) {
        this.address = address;
    }
}
