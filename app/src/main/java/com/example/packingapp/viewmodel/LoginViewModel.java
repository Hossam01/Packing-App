package com.example.packingapp.viewmodel;

import android.util.Log;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.ResponseLogin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<ResponseLogin> smsLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseLogin> getSmsLiveData() {
        return smsLiveData;
    }
    public static MutableLiveData<String> mutableLiveDataError = new MutableLiveData<>();

    public void fetchdata(String username, String password) {

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        ApiClient.build().loginwithno(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(responseSms -> {
                            smsLiveData.setValue(responseSms);
                        }
                        ,throwable -> {
                            mutableLiveDataError.setValue(throwable.getMessage());
                            Log.d("Error",throwable.getMessage());

                        });
    }
}
