package com.example.packingapp.model;


import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderDataModuleDB")
public class OrderDataModuleDB {
	@PrimaryKey(autoGenerate = true)
	private int uid;

	@ColumnInfo(name ="Order_number")
	@SerializedName("Order_number")
	private String Order_number;

	@ColumnInfo(name = "Customer_name")
	@SerializedName("Customer_name")
	private String Customer_name;

	@ColumnInfo(name = "Customer_phone")
	@SerializedName("Customer_phone")
	private String Customer_phone;

	@ColumnInfo(name = "Customer_address_en")
	@SerializedName("Customer_address_en")
	private String Customer_address_en;

	@ColumnInfo(name = "Customer_address_ar")
	@SerializedName("Customer_address_ar")
	private String Customer_address_ar;

	@ColumnInfo(name = "Delivery_date")
	@SerializedName("Delivery_date")
	private String Delivery_date;

	@ColumnInfo(name = "Time_slot")
	@SerializedName("Time_slot")
	private String Time_slot;

	@ColumnInfo(name = "Name_of_item")
	@SerializedName("Name_of_item")
	private String Name_of_item;

	@ColumnInfo(name = "Quantity")
	@SerializedName("Quantity")
	private String Quantity;

	@ColumnInfo(name = "Price")
	@SerializedName("Price")
	private String Price;


	@ColumnInfo(name = "Out_From_Loc")
	@SerializedName("Out_From_Loc")
	private String Out_From_Loc;

	@ColumnInfo(name = "tracking_number")
	@SerializedName("tracking_number")
	private String tracking_number;


	public OrderDataModuleDB() {
	}

	public OrderDataModuleDB(String order_number, String customer_name, String customer_phone, String customer_address_en, String customer_address_ar, String delivery_date, String time_slot, String name_of_item, String quantity, String price, String out_From_Loc, String tracking_number) {
		Order_number = order_number;
		Customer_name = customer_name;
		Customer_phone = customer_phone;
		Customer_address_en = customer_address_en;
		Customer_address_ar = customer_address_ar;
		Delivery_date = delivery_date;
		Time_slot = time_slot;
		Name_of_item = name_of_item;
		Quantity = quantity;
		Price = price;
		Out_From_Loc = out_From_Loc;
		this.tracking_number = tracking_number;
	}

	public OrderDataModuleDB(int uid, String order_number, String customer_name, String customer_phone, String customer_address_en, String customer_address_ar, String delivery_date, String time_slot, String name_of_item, String quantity, String price, String out_From_Loc, String tracking_number) {
		this.uid = uid;
		Order_number = order_number;
		Customer_name = customer_name;
		Customer_phone = customer_phone;
		Customer_address_en = customer_address_en;
		Customer_address_ar = customer_address_ar;
		Delivery_date = delivery_date;
		Time_slot = time_slot;
		Name_of_item = name_of_item;
		Quantity = quantity;
		Price = price;
		Out_From_Loc = out_From_Loc;
		this.tracking_number=tracking_number;
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

	public String getOut_From_Loc() {
		return Out_From_Loc;
	}

	public void setOut_From_Loc(String out_From_Loc) {
		Out_From_Loc = out_From_Loc;
	}
	public String getTracking_number() {
		return tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

}
