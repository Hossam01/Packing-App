package com.example.packingapp.UI;

import android.os.Bundle;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.databinding.ActivityMainBinding;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.RecordsItem;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    AppDatabase database;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database= AppDatabase.getDatabaseInstance(this);
        database.userDao().getUserData().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<RecordsItem>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<RecordsItem> recordsItems) {
             //   binding.text.setText(recordsItems.get(0).getUserDescription());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        database.userDao().getHeader().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<OrderDataModuleDBHeader>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(OrderDataModuleDBHeader recordsItems) {
                binding.text.setText(recordsItems.getOrder_number());
            }

            @Override
            public void onError(Throwable e) {
                binding.text.setText(e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });

    }
}