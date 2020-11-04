package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.databinding.ActivityEditPackageBinding;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditPackageActivity extends AppCompatActivity {
    AppDatabase database;

    ActivityEditPackageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database= AppDatabase.getDatabaseInstance(this);
        database.userDao().getAllItem(binding.item.getText().toString()).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<ItemsOrderDataDBDetails>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<ItemsOrderDataDBDetails> orderDataModuleDBS) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}