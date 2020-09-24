package com.example.packingapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "user")
public class RecordsItem {
	@PrimaryKey(autoGenerate = true)
	private int id;
	@ColumnInfo(name = "User_Department")
	@SerializedName("User_Department")
	private String userDepartment;
	@ColumnInfo(name = "User_status")
	@SerializedName("User_status")
	private String userStatus;
	@ColumnInfo(name = "ComplexID")
	@SerializedName("ComplexID")
	private String complexID;
	@ColumnInfo(name = "Group_Name")
	@SerializedName("Group_Name")
	private String groupName;
	@ColumnInfo(name = "company")
	@SerializedName("company")
	private String company;
	@ColumnInfo(name = "user_id")
	@SerializedName("id")
	private String user_id;
	@ColumnInfo(name = "User_Description")
	@SerializedName("User_Description")
	private String userDescription;
	@ColumnInfo(name = "GroupID")
	@SerializedName("GroupID")
	private String groupID;

	public RecordsItem() {
	}

	public RecordsItem(String userDepartment, String userStatus, String complexID, String groupName, String company, String user_id, String userDescription, String groupID) {
		this.userDepartment = userDepartment;
		this.userStatus = userStatus;
		this.complexID = complexID;
		this.groupName = groupName;
		this.company = company;
		this.user_id = user_id;
		this.userDescription = userDescription;
		this.groupID = groupID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

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
