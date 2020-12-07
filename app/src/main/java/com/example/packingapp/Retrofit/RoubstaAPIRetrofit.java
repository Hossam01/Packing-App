package com.example.packingapp.Retrofit;

import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;
import com.example.packingapp.model.ResponseUpdateStatus;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoubstaAPIRetrofit {

   /* @POST("api/json/get/4k-y_dgjY")  //Not Packed Yet
    //@POST("api/json/get/41AO__gsK")  //packed
    Observable<ResponseGetOrderData> GetOrderData(@Body Map<String, String> mobile);
    */

    @Headers("Accept:application/json")// @Headers("Accept: */*")//    @Headers("Accept: application/vnd.github.v3.full+json")
    @GET("ar_EG/V1/integration/orders/{ORDER_NO}")//@FormUrlEncoded// Picked
    Observable<ResponseGetOrderData> GetOrderData(
            @Header("Authorization") String Token ,
            @Path("ORDER_NO") String ORDER_NO
    );


    //@Headers("Accept:application/json")
   // @FormUrlEncoded
    @POST("V1/integration/orders/{ORDER_NO}/status")
    Observable<ResponseUpdateStatus> UpdateOrderStatus(
            @Header("Authorization") String Token,
            @Path("ORDER_NO") String ORDER_NO ,
            @Body Map<String, String> mobile
    );

}
