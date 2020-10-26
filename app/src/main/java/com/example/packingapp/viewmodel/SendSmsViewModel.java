package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.ResponseSms;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SendSmsViewModel extends ViewModel {
    private MutableLiveData<ResponseSms> smsLiveData = new MutableLiveData<>();
    public MutableLiveData<ResponseSms> getSmsLiveData() {
        return smsLiveData;
    }

    public void fetchdata(String number, String message) {
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
}
