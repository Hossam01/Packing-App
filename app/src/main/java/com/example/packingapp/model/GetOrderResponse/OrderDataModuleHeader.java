package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;

public class OrderDataModuleHeader  {
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

    @SerializedName("Out_From_Loc")
    private String Out_From_Loc;

	@SerializedName("items")
	private List<ItemsOrderDataDBDetails> itemsOrderDataDBDetails;

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
