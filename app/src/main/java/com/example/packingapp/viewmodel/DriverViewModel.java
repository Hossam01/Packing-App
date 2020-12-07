package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseVehicle;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DriverViewModel extends ViewModel {

    public static MutableLiveData<Message> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Message> getDriverLiveData() {
        return mutableLiveData;
    }

    public void fetchdata(String nameArabic, String nameEnglish, String status, String company, String Phone, String address, String Vechile_ID,String National_ID,String EmployeeID) {


        HashMap<String, String> map = new HashMap<>();
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("Status", status);
        map.put("Company", company);
        map.put("Phone", Phone);
        map.put("Address", address);
        map.put("Vechile_ID", Vechile_ID);
        map.put("National_ID", National_ID);
        map.put("EmployeeID", EmployeeID);
        ApiClient.build().createDriver(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }

    public void updateData(String id, String nameArabic, String nameEnglish, String status, String company, String Phone, String address, String Vechile_ID, String National_ID,String EmployeeID) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Driver_ID", id);
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("Status", status);
        map.put("Company", company);
        map.put("Phone", Phone);
        map.put("Address", address);
        map.put("Vechile_ID", Vechile_ID);
        map.put("National_ID", National_ID);
        map.put("EmployeeID", EmployeeID);

        ApiClient.build().updateDriver(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });

    }
    public static MutableLiveData<ResponseVehicle> mutableLiveDataVehicle = new MutableLiveData<>();

    public void fetchDataVehicle(){
        ApiClient.build().readVehicle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ResponseVehicle responseSms) -> {
                            mutableLiveDataVehicle.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });
    }
    public static MutableLiveData<ResponseDriver> mutableLiveDataRead = new MutableLiveData<>();


    public void fetchDataDriver(){
        ApiClient.build().readDriver()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ResponseDriver responseSms) -> {
                            mutableLiveDataRead.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });
    }


}
