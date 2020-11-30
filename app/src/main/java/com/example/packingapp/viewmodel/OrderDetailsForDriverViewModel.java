package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.DriverModules.DriverPackages_Respones_Details_recycler;
import com.example.packingapp.model.ResponseSms;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailsForDriverViewModel extends ViewModel {
    private MutableLiveData<ResponseSms> smsLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseSms> getSmsLiveData() {
        return smsLiveData;
    }

    public void SendSms(String number, String message) {
       ApiClient.build().sendSms(number,message)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(responseSms -> {
                   smsLiveData.setValue(responseSms);
                       }
               ,throwable -> {
                           Log.d("Error",throwable.getMessage());

                       });

    }

    private MutableLiveData<DriverPackages_Respones_Details_recycler> DriverOrderReadyDetailsDataLiveData = new MutableLiveData<>();
    public MutableLiveData<DriverPackages_Respones_Details_recycler> GetDriverOrdersReadyDetailsDataLiveData() {
        return DriverOrderReadyDetailsDataLiveData;
    }

    public static MutableLiveData<String> mutableDetailsLiveDataError = new MutableLiveData<>();

    public void ReadDriverRunsheetOrdersData(String ORDER_NO) {

        HashMap<String, String> map = new HashMap<>();
        map.put("ORDER_NO", ORDER_NO);


        ApiClient.build().ReadDriverTrackingnumbersOfOrders_83(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( (responseGetOrderData) -> {
                            DriverOrderReadyDetailsDataLiveData.setValue(responseGetOrderData);
                            //  Log.d(TAG, "fetchdata: "+responseGetOrderData);
                        }
                        ,throwable -> {
                            mutableDetailsLiveDataError.setValue(throwable.getMessage());
                            Log.d("Error",throwable.getMessage());

                        });
    }


}
