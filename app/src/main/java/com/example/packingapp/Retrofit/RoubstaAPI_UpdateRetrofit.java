package com.example.packingapp.Retrofit;

import com.example.packingapp.model.ResponseUpdateStatus;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoubstaAPI_UpdateRetrofit {
      @Headers("Accept:application/json")
   // @Headers("Accept: */*")
    @POST("{ORDER_NO}/status")
    @FormUrlEncoded
    Observable<ResponseUpdateStatus> UpdateOrderStatus(
              @Header("Authorization") String Token,
            @Path("ORDER_NO") String ORDER_NO ,
//            @Body Map<String, String> mobile
            @Field("status")  String status
    );

}
