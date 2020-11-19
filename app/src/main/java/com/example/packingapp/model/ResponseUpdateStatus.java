package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateStatus {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
