package com.example.packingapp.Retrofit;

import com.example.packingapp.model.Message;
import com.example.packingapp.model.OrderDataModuleDB;
import com.example.packingapp.model.ResponseGetOrderData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RoubstaAPIRetrofit {


    @POST("api/json/get/cfqynWUrIi?indent=2")
    Observable<OrderDataModuleDB> GetOrderData(@Body Map<String, String> mobile);



}
