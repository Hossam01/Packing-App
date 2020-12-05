package com.example.packingapp.model.GetOrderResponse;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class ErrorBodyForRoubstaAPI {
    @SerializedName("message")
    private String message;

    @SerializedName("parameters")
    private String parameters;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
