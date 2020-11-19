package com.example.packingapp.model.UploadAfterPrint;


import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class UploadOrderDataModule {

	private String ORDER_NO;
	private String OUTBOUND_DELIVERY;
	private String CUSTOMER_NAME;
	private String CUSTOMER_PHONE;
	private String CUSTOMER_CODE;
	private String ADDRESS_GOVERN;
	private String ADDRESS_CITY;
	private String ADDRESS_DISTRICT;
	private String ADDRESS_DETAILS;
	private String ORDER_DELIVERY_DATE;
	private String ORDER_DELIVERY_TIME;
	private String PICKER_CONFIMATION_TIME;
	private String GRAND_TOTAL;
	private String CURRENCY;
	private String SHIPPING_FEES;
	private String STORAGE_LOCATION;

	public UploadOrderDataModule(String ORDER_NO, String OUTBOUND_DELIVERY, String CUSTOMER_NAME,
								 String CUSTOMER_PHONE, String CUSTOMER_CODE, String ADDRESS_GOVERN,
								 String ADDRESS_CITY, String ADDRESS_DISTRICT, String ADDRESS_DETAILS,
								 String ORDER_DELIVERY_DATE, String ORDER_DELIVERY_TIME,
								 String PICKER_CONFIMATION_TIME,
								 String GRAND_TOTAL, String CURRENCY, String SHIPPING_FEES,
								 String STORAGE_LOCATION) {
		this.ORDER_NO = ORDER_NO;
		this.OUTBOUND_DELIVERY = OUTBOUND_DELIVERY;
		this.CUSTOMER_NAME = CUSTOMER_NAME;
		this.CUSTOMER_PHONE = CUSTOMER_PHONE;
		this.CUSTOMER_CODE = CUSTOMER_CODE;
		this.ADDRESS_GOVERN = ADDRESS_GOVERN;
		this.ADDRESS_CITY = ADDRESS_CITY;
		this.ADDRESS_DISTRICT = ADDRESS_DISTRICT;
		this.ADDRESS_DETAILS = ADDRESS_DETAILS;
		this.ORDER_DELIVERY_DATE = ORDER_DELIVERY_DATE;
		this.ORDER_DELIVERY_TIME = ORDER_DELIVERY_TIME;
		this.PICKER_CONFIMATION_TIME = PICKER_CONFIMATION_TIME;
		this.GRAND_TOTAL = GRAND_TOTAL;
		this.CURRENCY = CURRENCY;
		this.SHIPPING_FEES = SHIPPING_FEES;
		this.STORAGE_LOCATION = STORAGE_LOCATION;
	}

	public String getORDER_NO() {
		return ORDER_NO;
	}

	public void setORDER_NO(String ORDER_NO) {
		this.ORDER_NO = ORDER_NO;
	}

	public String getOUTBOUND_DELIVERY() {
		return OUTBOUND_DELIVERY;
	}

	public void setOUTBOUND_DELIVERY(String OUTBOUND_DELIVERY) {
		this.OUTBOUND_DELIVERY = OUTBOUND_DELIVERY;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
		this.CUSTOMER_NAME = CUSTOMER_NAME;
	}

	public String getCUSTOMER_PHONE() {
		return CUSTOMER_PHONE;
	}

	public void setCUSTOMER_PHONE(String CUSTOMER_PHONE) {
		this.CUSTOMER_PHONE = CUSTOMER_PHONE;
	}

	public String getCUSTOMER_CODE() {
		return CUSTOMER_CODE;
	}

	public void setCUSTOMER_CODE(String CUSTOMER_CODE) {
		this.CUSTOMER_CODE = CUSTOMER_CODE;
	}

	public String getADDRESS_GOVERN() {
		return ADDRESS_GOVERN;
	}

	public void setADDRESS_GOVERN(String ADDRESS_GOVERN) {
		this.ADDRESS_GOVERN = ADDRESS_GOVERN;
	}

	public String getADDRESS_CITY() {
		return ADDRESS_CITY;
	}

	public void setADDRESS_CITY(String ADDRESS_CITY) {
		this.ADDRESS_CITY = ADDRESS_CITY;
	}

	public String getADDRESS_DISTRICT() {
		return ADDRESS_DISTRICT;
	}

	public void setADDRESS_DISTRICT(String ADDRESS_DISTRICT) {
		this.ADDRESS_DISTRICT = ADDRESS_DISTRICT;
	}

	public String getADDRESS_DETAILS() {
		return ADDRESS_DETAILS;
	}

	public void setADDRESS_DETAILS(String ADDRESS_DETAILS) {
		this.ADDRESS_DETAILS = ADDRESS_DETAILS;
	}

	public String getORDER_DELIVERY_DATE() {
		return ORDER_DELIVERY_DATE;
	}

	public void setORDER_DELIVERY_DATE(String ORDER_DELIVERY_DATE) {
		this.ORDER_DELIVERY_DATE = ORDER_DELIVERY_DATE;
	}

	public String getORDER_DELIVERY_TIME() {
		return ORDER_DELIVERY_TIME;
	}

	public void setORDER_DELIVERY_TIME(String ORDER_DELIVERY_TIME) {
		this.ORDER_DELIVERY_TIME = ORDER_DELIVERY_TIME;
	}

	public String getPICKER_CONFIMATION_TIME() {
		return PICKER_CONFIMATION_TIME;
	}

	public void setPICKER_CONFIMATION_TIME(String PICKER_CONFIMATION_TIME) {
		this.PICKER_CONFIMATION_TIME = PICKER_CONFIMATION_TIME;
	}

	public String getGRAND_TOTAL() {
		return GRAND_TOTAL;
	}

	public void setGRAND_TOTAL(String GRAND_TOTAL) {
		this.GRAND_TOTAL = GRAND_TOTAL;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String CURRENCY) {
		this.CURRENCY = CURRENCY;
	}

	public String getSHIPPING_FEES() {
		return SHIPPING_FEES;
	}

	public void setSHIPPING_FEES(String SHIPPING_FEES) {
		this.SHIPPING_FEES = SHIPPING_FEES;
	}

	public String getSTORAGE_LOCATION() {
		return STORAGE_LOCATION;
	}

	public void setSTORAGE_LOCATION(String STORAGE_LOCATION) {
		this.STORAGE_LOCATION = STORAGE_LOCATION;
	}
}
