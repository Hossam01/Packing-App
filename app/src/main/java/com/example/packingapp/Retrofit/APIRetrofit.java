package com.example.packingapp.Retrofit;

import com.example.packingapp.model.*;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRetrofit {
    @POST("Packing_Api/Login/Auth.php")
    Observable<ResponseLogin> loginwithno(@Body Map<String, String> mobile);

    @POST("Packing_Api/Vechile/Create.php")
    Observable<Message> createVehicle(@Body Map<String, String> mobile);

    @POST("Packing_Api/Driver/Create.php")
    Observable<Message> createDriver(@Body Map<String, String> mobile);

    @POST("Packing_Api/Direction/Create.php")
    Observable<Message> createDirection(@Body Map<String, String> mobile);

    @GET("Packing_Api/Driver/Read.php")
    Observable<ResponseDriver> readDriver();

    @GET("Packing_Api/Vechile/Read.php")
    Observable<ResponseVehicle> readVehicle();
}
