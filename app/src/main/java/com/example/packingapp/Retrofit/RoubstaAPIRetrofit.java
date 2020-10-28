package com.example.packingapp.Retrofit;

import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseGetOrderData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RoubstaAPIRetrofit {


    @POST("NyH8s6gut")
    Observable<ResponseGetOrderData> GetOrderData(@Body Map<String, String> mobile);



}
