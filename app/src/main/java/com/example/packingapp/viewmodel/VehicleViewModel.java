package com.example.packingapp.viewmodel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.packingapp.Retrofit.APIRetrofit;
import com.example.packingapp.Retrofit.ApiClient;
import com.example.packingapp.model.Message;
import com.example.packingapp.model.ResponseVehicle;
import com.example.packingapp.workmanagerapi.ReadDataWorkManageVehicle;
import com.example.packingapp.workmanagerapi.UpdateWorkerManagerApiVehicle;
import com.example.packingapp.workmanagerapi.WorkerManagerApiLogin;
import com.example.packingapp.workmanagerapi.WorkerManagerApiVehicle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VehicleViewModel extends ViewModel {

    public void fetchdata(String nameArabic, String nameEnglish, String lienceNumber, String weight) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("NameArabic", nameArabic);
        data.putString("NameEnglish", nameEnglish);
        data.putString("LienceNumber", lienceNumber);
        data.putString("Weight", weight);


        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkerManagerApiVehicle.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);

    }
    public void fetchDataVehicle(){
        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ReadDataWorkManageVehicle.class)
                .addTag("Sync")
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);
    }
    public void updateData(String id, String nameArabic, String nameEnglish, String lienceNumber, String weight) {


        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED);
        Data.Builder data = new Data.Builder();
        data.putString("Vechile_ID", id);
        data.putString("NameArabic", nameArabic);
        data.putString("NameEnglish", nameEnglish);
        data.putString("LienceNumber", lienceNumber);
        data.putString("Weight", weight);

        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UpdateWorkerManagerApiVehicle.class)
                .addTag("Sync")
                .setInputData(data.build())
                .setConstraints(builder.build())
                .build();
        mWorkManager.enqueue(workRequest);

    }
}
