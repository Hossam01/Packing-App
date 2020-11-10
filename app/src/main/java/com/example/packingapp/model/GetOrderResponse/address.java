package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

public class address {

    @SerializedName("govern")
    private String govern;

    @SerializedName("city")
    private String city;

    @SerializedName("district")
    private String district;

    @SerializedName("address")
    private String customer_address_detail;

    public String getGovern() {
        return govern;
    }

    public void setGovern(String govern) {
        this.govern = govern;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCustomer_address_detail() {
        return customer_address_detail;
    }

    public void setCustomer_address_detail(String customer_address_detail) {
        this.customer_address_detail = customer_address_detail;
    }
}
