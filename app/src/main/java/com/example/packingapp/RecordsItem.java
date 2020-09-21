package com.example.packingapp;

import com.google.gson.annotations.SerializedName;

public class RecordsItem {
	@SerializedName("User_Department")
	private String userDepartment;
	@SerializedName("User_status")
	private String userStatus;
	@SerializedName("ComplexID")
	private String complexID;
	@SerializedName("Group_Name")
	private String groupName;
	@SerializedName("company")
	private String company;
	@SerializedName("id")
	private String id;
	@SerializedName("User_Description")
	private String userDescription;
	@SerializedName("GroupID")
	private String groupID;

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setComplexID(String complexID) {
		this.complexID = complexID;
	}

	public String getComplexID() {
		return complexID;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return company;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupID() {
		return groupID;
	}
}
