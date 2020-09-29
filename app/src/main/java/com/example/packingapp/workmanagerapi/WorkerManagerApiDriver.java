package com.example.packingapp.workmanagerapi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.packingapp.Retrofit.APIRetrofit;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Message;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WorkerManagerApiDriver extends Worker {
    public static MutableLiveData<Message> mutableLiveData = new MutableLiveData<>();

    public WorkerManagerApiDriver(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String nameArabic = getInputData().getString("NameArabic");
        String nameEnglish = getInputData().getString("NameEnglish");
        String status = getInputData().getString("Status");
        String company = getInputData().getString("Company");
        String phone = getInputData().getString("Phone");
        String address = getInputData().getString("Address");
        String vechileId = getInputData().getString("Vechile_ID");


        HashMap<String, String> map = new HashMap<>();
        map.put("NameArabic", nameArabic);
        map.put("NameEnglish", nameEnglish);
        map.put("Status", status);
        map.put("Company", company);
        map.put("Phone", phone);
        map.put("Address", address);
        map.put("Vechile_ID", vechileId);


        ApiClient apiClient = new ApiClient();
        APIRetrofit client = apiClient.build().create(APIRetrofit.class);
        Observable<Message> call=client.createDriver(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<Message> observer=new Observer<Message>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                mutableLiveData.setValue(message);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        call.subscribe(observer);

        return Result.success();
    }
}
