package com.example.packingapp.viewmodel;

import android.util.Log;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.GetOrderResponse.ResponseGetOrderData;

import java.util.HashMap;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecievePackedOrderViewModel {
    private MutableLiveData<ResponseGetOrderData> OrderDataLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseGetOrderData> getOrderDataLiveData() {
        return OrderDataLiveData;
    }

    public static MutableLiveData<String> mutableLiveDataError = new MutableLiveData<>();

    public void fetchdata(String OrderNumber) {

        HashMap<String, String> map = new HashMap<>();
        map.put("OrderNumber", OrderNumber);

        ApiClient.buildRo().GetOrderData(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( (responseGetOrderData) -> {
                            OrderDataLiveData.setValue(responseGetOrderData);
                            //  Log.d(TAG, "fetchdata: "+responseGetOrderData);
                        }
                        ,throwable -> {
                            mutableLiveDataError.setValue(throwable.getMessage());
                            Log.d("Error",throwable.getMessage());

                        });
    }
}
