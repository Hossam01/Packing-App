package com.example.packingapp.model.DriverModules;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="DriverPackages_Header_DB")
public class DriverPackages_Header_DB {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name ="ORDER_NO")
    @SerializedName("ORDER_NO")
    private String Order_number;

    @ColumnInfo(name ="Passcode")
    @SerializedName("Passcode")
    private String Passcode;

    @ColumnInfo(name = "Customer_name")
    @SerializedName("Customer_name")
    private String Customer_name;

    @ColumnInfo(name = "CUSTOMER_PHONE")
    @SerializedName("CUSTOMER_PHONE")
    private String Customer_phone;

    @ColumnInfo(name = "Customer_code")
    @SerializedName("Customer_code")
    private String Customer_code;

    @ColumnInfo(name = "Customer_address_govern")
    @SerializedName("Customer_address_govern")
    private String Customer_address_govern;


    @ColumnInfo(name = "Customer_address_city")
    @SerializedName("Customer_address_city")
    private String Customer_address_city;

    @ColumnInfo(name = "Customer_address_district")
    @SerializedName("Customer_address_district")
    private String Customer_address_district;

    @ColumnInfo(name = "Customer_address_detail")
    @SerializedName("Customer_address_detail")
    private String Customer_address_detail;


    @ColumnInfo(name = "delivery_date")
    @SerializedName("delivery_date")
    private String delivery_date;

    @ColumnInfo(name = "delivery_time")
    @SerializedName("delivery_time")
    private String delivery_time;

    @ColumnInfo(name = "grand_total")
    @SerializedName("grand_total")
    private String grand_total;

    @ColumnInfo(name = "shipping_fees")
    @SerializedName("shipping_fees")
    private String shipping_fees;

    @ColumnInfo(name = "NO_OF_PACKAGES")
    @SerializedName("NO_OF_PACKAGES")
    private String NO_OF_PACKAGES;

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    private String currency;

    @ColumnInfo(name = "Out_From_Loc")
    @SerializedName("Out_From_Loc")
    private String Out_From_Loc;

    public DriverPackages_Header_DB() {
    }

    public DriverPackages_Header_DB(String Passcode) {
        this.Passcode=Passcode;
    }

    public DriverPackages_Header_DB(String order_number, String customer_name, String customer_phone,
                                   String customer_code, String customer_address_govern,
                                   String customer_address_city, String customer_address_district,
                                   String customer_address_detail, String delivery_date,
                                   String delivery_time,
                                   String grand_total, String shipping_fees,
                                   String NO_OF_PACKAGES, String currency,String Out_From_Loc) {
        Order_number = order_number;
        Customer_name = customer_name;
        Customer_phone = customer_phone;
        Customer_code = customer_code;
        Customer_address_govern = customer_address_govern;
        Customer_address_city = customer_address_city;
        Customer_address_district = customer_address_district;
        Customer_address_detail = customer_address_detail;
        this.delivery_date = delivery_date;
        this.delivery_time = delivery_time;
        this.grand_total = grand_total;
        this.shipping_fees = shipping_fees;
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
        this.currency = currency;
        this.Out_From_Loc= Out_From_Loc;
    }

    public String getShipping_fees() {
        return shipping_fees;
    }

    public void setShipping_fees(String shipping_fees) {
        this.shipping_fees = shipping_fees;
    }

    public String getNO_OF_PACKAGES() {
        return NO_OF_PACKAGES;
    }

    public void setNO_OF_PACKAGES(String NO_OF_PACKAGES) {
        this.NO_OF_PACKAGES = NO_OF_PACKAGES;
    }

    public String getCustomer_code() {
        return Customer_code;
    }

    public void setCustomer_code(String customer_code) {
        Customer_code = customer_code;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOrder_number() {
        return Order_number;
    }

    public String getPasscode() {
        return Passcode;
    }

    public void setPasscode(String outBound_delivery) {
        Passcode = outBound_delivery;
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

    public String getCustomer_address_govern() {
        return Customer_address_govern;
    }

    public void setCustomer_address_govern(String customer_address_govern) {
        Customer_address_govern = customer_address_govern;
    }

    public String getCustomer_address_city() {
        return Customer_address_city;
    }

    public void setCustomer_address_city(String customer_address_city) {
        Customer_address_city = customer_address_city;
    }

    public String getCustomer_address_district() {
        return Customer_address_district;
    }

    public void setCustomer_address_district(String customer_address_district) {
        Customer_address_district = customer_address_district;
    }

    public String getCustomer_address_detail() {
        return Customer_address_detail;
    }

    public void setCustomer_address_detail(String customer_address_detail) {
        Customer_address_detail = customer_address_detail;
    }

    public String getOut_From_Loc() {
        return Out_From_Loc;
    }

    public void setOut_From_Loc(String out_From_Loc) {
        Out_From_Loc = out_From_Loc;
    }
}
