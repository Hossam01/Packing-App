package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ModulesIDS")
public class ModulesIDS {

        @PrimaryKey(autoGenerate = true)
        private int uid;
        @ColumnInfo(name = "Modules_ID")
        @SerializedName("Modules_ID")
        private String Modules_ID;


    public ModulesIDS() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getModules_ID() {
        return Modules_ID;
    }

    public void setModules_ID(String modules_ID) {
        Modules_ID = modules_ID;
    }
}
