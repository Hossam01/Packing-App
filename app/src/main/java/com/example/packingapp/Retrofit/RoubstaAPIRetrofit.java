package com.example.packingapp.Retrofit;

import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RoubstaAPIRetrofit {


    @POST("api/json/get/4kZmLJF_Y")
    Observable<ResponseGetOrderData> GetOrderData(@Body Map<String, String> mobile);



}
