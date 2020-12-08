package com.example.packingapp.model.TimeSheet;

import com.google.gson.annotations.SerializedName;

public class RecordsItem {
	@SerializedName("OUTBOUND_DELIVERY")
	private String OUTBOUND_DELIVERY;
	@SerializedName("STATUS")
	private String STATUS;
	@SerializedName("NO_OF_PACKAGES")
	private String NO_OF_PACKAGES;
	@SerializedName("CUSTOMER_NAME")
	private String CUSTOMER_NAME;
	@SerializedName("TRACKING_NO")
	private String TRACKING_NO;
	@SerializedName("ORDER_NO")
	private String ORDER_NO;
	@SerializedName("ADDRESS_CITY")
	private String ADDRESS_CITY;
	@SerializedName("ITEM_PRICE")
	private String ITEM_PRICE;

	public String getOUTBOUND_DELIVERY() {
		return OUTBOUND_DELIVERY;
	}

	public void setOUTBOUND_DELIVERY(String OUTBOUND_DELIVERY) {
		this.OUTBOUND_DELIVERY = OUTBOUND_DELIVERY;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
	}

	public String getNO_OF_PACKAGES() {
		return NO_OF_PACKAGES;
	}

	public void setNO_OF_PACKAGES(String NO_OF_PACKAGES) {
		this.NO_OF_PACKAGES = NO_OF_PACKAGES;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
		this.CUSTOMER_NAME = CUSTOMER_NAME;
	}

	public String getTRACKING_NO() {
		return TRACKING_NO;
	}

	public void setTRACKING_NO(String TRACKING_NO) {
		this.TRACKING_NO = TRACKING_NO;
	}

	public String getORDER_NO() {
		return ORDER_NO;
	}

	public void setORDER_NO(String ORDER_NO) {
		this.ORDER_NO = ORDER_NO;
	}

	public String getADDRESS_CITY() {
		return ADDRESS_CITY;
	}

	public void setADDRESS_CITY(String ADDRESS_CITY) {
		this.ADDRESS_CITY = ADDRESS_CITY;
	}

	public String getITEM_PRICE() {
		return ITEM_PRICE;
	}

	public void setITEM_PRICE(String ITEM_PRICE) {
		this.ITEM_PRICE = ITEM_PRICE;
	}
}
