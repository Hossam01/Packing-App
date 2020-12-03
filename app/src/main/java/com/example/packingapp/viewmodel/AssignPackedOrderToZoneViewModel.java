package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseUpdateStatus;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AssignPackedOrderToZoneViewModel extends ViewModel {
    private MutableLiveData<RecievePackedModule> OrderDataLiveData = new MutableLiveData<>();
    public MutableLiveData<RecievePackedModule> getOrderDataLiveData() {
        return OrderDataLiveData;
    }

    public static MutableLiveData<String> mutableLiveDataError = new MutableLiveData<>();

    public void fetchdata(String OrderNumber) {

        HashMap<String, String> map = new HashMap<>();
        map.put("ORDER_NO", OrderNumber);

        ApiClient.build().GetOrderNumberAndNumPackage(map)
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

    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateStatus = new MutableLiveData<>();

    public void UpdateStatus(String ORDER_NO, String Status) {


        HashMap<String, String> map = new HashMap<>();
        map.put("status", Status);

        ApiClient.RoubstaAPIRetrofit_UpdateStatus().UpdateOrderStatus(ORDER_NO ,
                "Bearer lnv0klr00jkprbugmojf3smj4i5gnn71",
                Status
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateStatus.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }

    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateStatus_Zone_ON_83 = new MutableLiveData<>();

    public void UpdateOrderStatus_Zone_ON_83(String ORDER_NO, String ZONE, String Status) {


        String text=ZONE+"/"+ORDER_NO+"/"+Status;
        ApiClient.build().UpdateOrderStatus_Zone_ON_83(text)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateStatus_Zone_ON_83.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }
    public static MutableLiveData<ResponseDriver> mutableLiveDataRead = new MutableLiveData<>();

    public void GetDriversID(){
        ApiClient.build().GetDrivers_IDS()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ResponseDriver responseSms) -> {
                            mutableLiveDataRead.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });
    }

    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateDriverID_ON_83 = new MutableLiveData<>();

    public void UpdateOrder_DriverID_ON_83(String ORDER_NO, String DriverID) {


        String text=ORDER_NO+"/"+DriverID;
        ApiClient.build().UpdateOrder_DriverID_83(text)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateDriverID_ON_83.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }

}
