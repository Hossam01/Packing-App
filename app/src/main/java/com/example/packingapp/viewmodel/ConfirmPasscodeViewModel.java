package com.example.packingapp.viewmodel;

import android.util.Log;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.DriverModules.DriverPackages_Details_DB;
import com.example.packingapp.model.ResponseUpdateStatus;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConfirmPasscodeViewModel extends ViewModel {

    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateStatus_PASSCODE_ON_83 = new MutableLiveData<>();

    public void UpdateOrderStatus_Passcode_Header_ON_83(String ORDER_NO, String PASSCODE, String Status) {


        HashMap<String, String> map = new HashMap<>();
        map.put("ORDER_NO", ORDER_NO);
        map.put("PASSCODE", PASSCODE);
        map.put("STATUS", Status);
        ApiClient.build().UpdateOrderStatus_PASSCODE_ON_83(map)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateStatus_PASSCODE_ON_83.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }


    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateStatus_Reason_ON_83 = new MutableLiveData<>();

    public void UpdateOrderStatus_Reason_Details_ON_83(List<DriverPackages_Details_DB> driverPackages_details_dbList) {
        HashMap<String, String> map = new HashMap<>();
//        map.put("ORDER_NO", ORDER_NO);
//        map.put("PASSCODE", PASSCODE);
//        map.put("STATUS", Status);



        ApiClient.build().UpdateOrderStatus_Reasone_ON_83(map)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateStatus_Reason_ON_83.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }
}
