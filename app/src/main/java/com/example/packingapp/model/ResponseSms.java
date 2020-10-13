package com.example.packingapp.model;

import com.google.gson.annotations.SerializedName;

public class ResponseSms {

    @SerializedName("ResultStatus")
    private String resultStatus;

    @SerializedName("SMSStatus")
    private String sMSStatus;

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getSMSStatus() {
        return sMSStatus;
    }

    public void setSMSStatus(String sMSStatus) {
        this.sMSStatus = sMSStatus;
    }
}