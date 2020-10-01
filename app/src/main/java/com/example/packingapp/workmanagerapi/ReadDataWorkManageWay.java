package com.example.packingapp.workmanagerapi;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.packingapp.Retrofit.APIRetrofit;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.ResponseDriver;
import com.example.packingapp.model.ResponseWay;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReadDataWorkManageWay extends Worker {
    public static MutableLiveData<ResponseWay> mutableLiveData = new MutableLiveData<>();

    public ReadDataWorkManageWay(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApiClient apiClient = new ApiClient();
        APIRetrofit client = apiClient.build().create(APIRetrofit.class);
        Observable<ResponseWay> call = client.readWay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<ResponseWay> observer = new Observer<ResponseWay>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseWay value) {
                mutableLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("data", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        call.subscribe(observer);
            return Result.success();
    }
}
