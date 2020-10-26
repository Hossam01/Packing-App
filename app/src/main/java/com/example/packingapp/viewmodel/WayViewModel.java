package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseVehicle;
import com.example.packingapp.model.ResponseWay;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WayViewModel extends ViewModel {
    public static MutableLiveData<Message> mutableLiveData = new MutableLiveData<>();

    public void fetchdata(String nameArabic, String nameEnglish, String status, String estimationTime, String stations, String driverId, String Vechile_ID) {
        HashMap<String, String> map = new HashMap<>();
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("Status", status);
        map.put("Estimation_Time", estimationTime);
        map.put("Stations", stations);
        map.put("Driver_ID", driverId);
        map.put("Vechile_ID", Vechile_ID);

        ApiClient.build().createDirection(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((Message responseSms) -> {
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

    public static MutableLiveData<ResponseWay> mutableLiveDataWay = new MutableLiveData<>();

    public void fetchDataWay(){
        ApiClient.build().readWay()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ResponseWay responseSms) -> {
                            mutableLiveDataWay.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });
    }

    public void updateData(String Direction_ID,String nameArabic, String nameEnglish, String status, String estimationTime, String stations, String driverId, String Vechile_ID) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Direction_ID", Direction_ID);
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("Status", status);
        map.put("Estimation_Time", estimationTime);
        map.put("Stations", stations);
        map.put("Driver_ID", driverId);
        map.put("Vechile_ID", Vechile_ID);

        ApiClient.build().updateWay(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            mutableLiveData.setValue(responseSms);
                        }
                        ,throwable -> {
                            Log.d("Error",throwable.getMessage());

                        });
    }
}
