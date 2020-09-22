package com.example.packingapp.Retrofit;

import com.example.packingapp.model.Response;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIRetrofit {
    @POST("api/Login/Auth.php")
    Observable<Response> loginwithno(@Body Map<String, String> mobile);
}
