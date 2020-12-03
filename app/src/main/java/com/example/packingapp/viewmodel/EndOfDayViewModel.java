package com.example.packingapp.viewmodel;

import android.util.Log;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.DriverModules.ResponeEndOfDay;

import java.util.HashMap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EndOfDayViewModel extends ViewModel {

    private MutableLiveData<ResponeEndOfDay> DriverrEndOfDayLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponeEndOfDay> DriverOrdersReadyDataLiveData() {
        return DriverrEndOfDayLiveData;
    }

    public static MutableLiveData<String> mutableLiveDataError = new MutableLiveData<>();

    public void GetOrderForEndOfDay_ON_83(String DRIVER_ID) {

        HashMap<String, String> map = new HashMap<>();
        map.put("DRIVER_ID", DRIVER_ID);

        ApiClient.build().GetOrderForEndOfDay_ON_83(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( (responseGetOrderData) -> {
                            DriverrEndOfDayLiveData.setValue(responseGetOrderData);
                            //  Log.d(TAG, "fetchdata: "+responseGetOrderData);
                        }
                        ,throwable -> {
                            mutableLiveDataError.setValue(throwable.getMessage());
                            Log.d("Error",throwable.getMessage());

                        });
    }
}
