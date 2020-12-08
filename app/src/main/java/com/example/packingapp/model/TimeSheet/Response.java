package com.example.packingapp.model.TimeSheet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response{
	@SerializedName("records")

	private List<RecordsItem> records;

	public void setRecords(List<RecordsItem> records){
		this.records = records;
	}

	public List<RecordsItem> getRecords(){
		return records;
	}
}