package com.example.packingapp.model.GetOrderResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseGetOrderData {

    @SerializedName("records")
    private OrderDataModuleHeader recorders;

    public ResponseGetOrderData(OrderDataModuleHeader recorders) {
        this.recorders = recorders;
    }

    public OrderDataModuleHeader getRecorders() {
        return recorders;
    }

    public void setRecorders(OrderDataModuleHeader recorders) {
        this.recorders = recorders;
    }
}
