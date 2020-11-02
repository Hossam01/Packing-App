package com.example.packingapp.model.GetOrderResponse;


import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderDataModuleDBHeader")
public class OrderDataModuleDBHeader {

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

	@ColumnInfo(name = "Out_From_Loc")
	@SerializedName("Out_From_Loc")
	private String Out_From_Loc;



	public String getOut_From_Loc() {
		return Out_From_Loc;
	}

	public void setOut_From_Loc(String out_From_Loc) {
		Out_From_Loc = out_From_Loc;
	}

//	public ItemsOrderDataDBDetails getItemsOrderDataDBDetails() {
//		return itemsOrderDataDBDetails;
//	}
//
//	public void setItemsOrderDataDBDetails(ItemsOrderDataDBDetails itemsOrderDataDBDetails) {
//		this.itemsOrderDataDBDetails = itemsOrderDataDBDetails;
//	}

//	@ColumnInfo(name = "Delivery_date")
//	@SerializedName("Delivery_date")
//	private String Delivery_date;
//
//	@ColumnInfo(name = "Time_slot")
//	@SerializedName("Time_slot")
//	private String Time_slot;
//
//	@ColumnInfo(name = "Name_of_item")
//	@SerializedName("Name_of_item")
//	private String Name_of_item;
//
//	@ColumnInfo(name = "Quantity")
//	@SerializedName("Quantity")
//	private String Quantity;
//
//	@ColumnInfo(name = "Price")
//	@SerializedName("Price")
//	private String Price;

//
//	@ColumnInfo(name = "tracking_number")
//	@SerializedName("tracking_number")
//	private String tracking_number;


	public OrderDataModuleDBHeader() {
	}

	public OrderDataModuleDBHeader( String order_number, String customer_name, String customer_phone,
								   String customer_address_en, String customer_address_ar) {
		Order_number = order_number;
		Customer_name = customer_name;
		Customer_phone = customer_phone;
		Customer_address_en = customer_address_en;
		Customer_address_ar = customer_address_ar;

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


}
