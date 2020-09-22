package com.example.packingapp.workmanagerapi;

import android.content.Context;
import android.util.Log;

import com.example.packingapp.Retrofit.APIRetrofit;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Response;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WorkerManagerApiLogin extends Worker {
    public static MutableLiveData<Response> mutableLiveData = new MutableLiveData<>();
    public static MutableLiveData<String> mutableLiveDataError = new MutableLiveData<>();

    public WorkerManagerApiLogin(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String username = getInputData().getString("username");
        String password = getInputData().getString("password");

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);


        ApiClient apiClient = new ApiClient();
        APIRetrofit client = apiClient.build().create(APIRetrofit.class);
        Observable<Response> call = client.loginwithno(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<Response> observer = new Observer<Response>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response value) {
                mutableLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("data", e.getMessage());
                mutableLiveDataError.setValue(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        call.subscribe(observer);

        return Result.success();
    }
}
