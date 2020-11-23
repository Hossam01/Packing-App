package com.example.packingapp.Retrofit;

import com.example.packingapp.model.Message;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseLogin;
import com.example.packingapp.model.ResponseSms;
import com.example.packingapp.model.ResponseVehicle;
import com.example.packingapp.model.ResponseWay;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRetrofit {

    @POST("Packing_Api/Login/Auth.php")
    Observable<ResponseLogin> loginwithno(@Body Map<String, String> mobile);

    @FormUrlEncoded
    @POST("Packing_Api/Vodafone/SendSMS.php")
    Observable<ResponseSms> sendSms(@Field("number") String phone, @Field("message") String message);

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

    @GET("Packing_Api/Direction/Read.php")
    Observable<ResponseWay> readWay();

    @POST("Packing_Api/Vechile/Update.php")
    Observable<Message> updateVehicle(@Body Map<String, String> mobile);

    @POST("Packing_Api/Driver/Update.php")
    Observable<Message> updateDriver(@Body Map<String, String> mobile);

    @POST("Packing_Api/Direction/Update.php")
    Observable<Message> updateWay(@Body Map<String, String> mobile);


    @POST("Packing_Api/Inout/create.php")
    Observable<Message> createInOut(@Body Map<String, String> mobile);

    @POST("Packing_Api/Ordernumber/CreateHeader.php")
    Observable<Message> InsertOrderDataHeader(@Body Map<String, String> mobile);

    @POST("Packing_Api/Ordernumber/CreateDetails.php")
    Observable<Message> InsertOrderDataDetails(@Body Map<String, String> mobile);

    @POST("Packing_Api/Ordernumber/Read.php")
    Observable<RecievePackedModule> GetOrderNumberAndNumPackage(@Body Map<String, String> mobile);

}
