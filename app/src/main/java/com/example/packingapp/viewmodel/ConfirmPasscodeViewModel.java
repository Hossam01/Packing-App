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
    private static final String TAG = "ConfirmPasscodeViewMode";
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
                            Log.d("ErrorHeader",throwable.getMessage());

                        });

    }


    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateStatus_Reason_ON_83 = new MutableLiveData<>();

    public void UpdateOrderStatus_Reason_Details_ON_83(List<DriverPackages_Details_DB> driverPackages_details_dbList) {
        HashMap<String, String> map = new HashMap<>();
//        map.put("ORDER_NO", ORDER_NO);
//        map.put("PASSCODE", PASSCODE);
//        map.put("STATUS", Status);

        if (driverPackages_details_dbList.size()>0) {
            for (int i = 0; i < driverPackages_details_dbList.size(); i++)
            {
                String text=driverPackages_details_dbList.get(i).getTRACKING_NO()
                        +"/"+driverPackages_details_dbList.get(i).getREASON()
                        +"/"+driverPackages_details_dbList.get(i).getSTATUS();

        ApiClient.build().UpdateOrderStatus_Reasone_ON_83(text)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateStatus_Reason_ON_83.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("ErrorDet",throwable.getMessage());

                        });
            }
        }
    }


    public static MutableLiveData<ResponseUpdateStatus> mutableLiveData_UpdateStatus = new MutableLiveData<>();

    public void UpdateStatus(String ORDER_NO, String status) {
        HashMap<String, String> map = new HashMap<>();
        map.put("status", status);

        Log.e(TAG, "UpdateStatus:rob "+ ORDER_NO);

        ApiClient.buildRo().UpdateOrderStatus(
                "Bearer lnv0klr00jkprbugmojf3smj4i5gnn71",
                ORDER_NO ,
                map
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData_UpdateStatus.setValue(responseSms);

                        }
                        ,throwable -> {
                            Log.d("Error_rou",throwable.getMessage());

                        });

    }


}
