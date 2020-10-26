package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseVehicle;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VehicleViewModel extends ViewModel {
    public static MutableLiveData<Message> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Message> getDriverLiveData() {
        return mutableLiveData;
    }
    public void fetchdata(String nameArabic, String nameEnglish, String lienceNumber, String weight) {


        HashMap<String, String> map = new HashMap<>();
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("LienceNumber", lienceNumber);
        map.put("Weight", weight);
        ApiClient.build().createVehicle(map)
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

    public void updateData(String id, String nameArabic, String nameEnglish, String lienceNumber, String weight) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Vechile_ID", id);
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("LienceNumber", lienceNumber);
        map.put("Weight", weight);
        ApiClient.build().updateVehicle(map)
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
