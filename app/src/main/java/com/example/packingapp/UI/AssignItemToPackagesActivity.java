package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Adapter.ItemAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityAssignItemsToPackageBinding;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.Product;
import com.example.packingapp.model.TrackingnumbersListDB;

import java.util.ArrayList;
import java.util.List;

public class AssignItemToPackagesActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";
    ActivityAssignItemsToPackageBinding binding;
    ItemAdapter itemAdapter;
    Product product;
    AppDatabase database;

    List<String> ListOfBarcodesToAssign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignItemsToPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        ListOfBarcodesToAssign=new ArrayList<>();

        itemAdapter = new ItemAdapter();
        binding.recyclerView.setAdapter(itemAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.imagSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.editBarcode.getText().toString().isEmpty()) {

                    List<ItemsOrderDataDBDetails> itemsOrderDataDBDetailsList =database.userDao().getItem(binding.editBarcode.getText().toString());
//                    product = new Product("laptop", "5");

                    if (itemsOrderDataDBDetailsList.size() >0 ) {
                            List<ItemsOrderDataDBDetails> Adapterlist = itemAdapter.ReturnListOfAdapter();
                            List<String> listClone = new ArrayList<>();

                            for (ItemsOrderDataDBDetails itemsOrderDataDBDetailsSE : Adapterlist) {
                                //matches("(?i)"+binding.editBarcode.getText().toString()+".*")
                                if (itemsOrderDataDBDetailsSE.getSku().toString().equalsIgnoreCase(binding.editBarcode.getText().toString())) {
                                    listClone.add(itemsOrderDataDBDetailsSE.getSku());
                                }
                            }
                            System.out.println(listClone.size());
                            System.out.println(binding.editBarcode.getText().toString());
                            //Log.e(TAG, "onClick: ", "" + binding.editBarcode.getText().toString() );
                            if (listClone.size() == 0 && itemsOrderDataDBDetailsList.get(0).getTrackingNumber() ==null ) {
                                itemAdapter.fillAdapterData(itemsOrderDataDBDetailsList.get(0));
                                ListOfBarcodesToAssign.add(binding.editBarcode.getText().toString());
                                binding.editBarcode.setText("");
                                binding.editBarcode.requestFocus();
                            } else {
                                binding.editBarcode.setError("تم أضافه هذا من قبل");
                                binding.editBarcode.requestFocus();
                            }

                    }else {
                        Toast.makeText(AssignItemToPackagesActivity.this, "هذا الباركود غير موجود", Toast.LENGTH_SHORT).show();
                        binding.editBarcode.setError(getResources().getString(R.string.enter_valid_barcode));
                        binding.editBarcode.requestFocus();
                    }
                } else {
                    binding.editBarcode.setError(getResources().getString(R.string.enter_barcode));
                    binding.editBarcode.requestFocus();
                }
            }
        });

        binding.btnAssignItemsToPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ItemsOrderDataDBDetails orderDataModuleDB=new
//                        ItemsOrderDataDBDetails("1452","ahmed","123","cairo","القاهره",
//                        "1-5","00:00","pizza","5","10","zayed","order_num+");
//                database.userDao().insertOrder(orderDataModuleDB);
                List <ItemsOrderDataDBDetails> Adapterlist = itemAdapter.ReturnListOfAdapter();

                if (Adapterlist.size() >0 && ListOfBarcodesToAssign.size() >0) {
//                    Log.e(TAG, "onClick: " + database.userDao().getLastTrackingnumber().getTrackingNumber());
                    if (database.userDao().getLastTrackingnumber() == null) {
                        Log.e(TAG, "onClick:ordernum  " + database.userDao().getOrderNumber() + "-01");
                        String Trackingnumber = database.userDao().getOrderNumber() + "-01";

                        database.userDao().Insertrackingnumber(new TrackingnumbersListDB(database.userDao().getOrderNumber() + "-01"));
                        database.userDao().updatetrackingnumberforListOfItems(Trackingnumber,ListOfBarcodesToAssign);


                    } else {
                        String LastTrackingNumber = database.userDao().getLastTrackingnumber().getTrackingNumber();
                        Log.e(TAG, "onClick:else " + LastTrackingNumber.substring(LastTrackingNumber.indexOf("-") + 1));
                        int num = Integer.valueOf(LastTrackingNumber.substring(LastTrackingNumber.indexOf("-") + 1));
                        ++num;
                        Log.e(TAG, "onClick:else+1  " + num);
                        String Trackingnumber;
                        if (num < 10) {
                            Trackingnumber = database.userDao().getOrderNumber() + "-0" + num;
                            database.userDao().Insertrackingnumber(new TrackingnumbersListDB(Trackingnumber));
                            database.userDao().updatetrackingnumberforListOfItems(Trackingnumber,ListOfBarcodesToAssign);
                        } else {
                            Trackingnumber = database.userDao().getOrderNumber() + "-" + num;
                            database.userDao().Insertrackingnumber(new TrackingnumbersListDB(Trackingnumber));
                            database.userDao().updatetrackingnumberforListOfItems(Trackingnumber,ListOfBarcodesToAssign);


                        }
                        Log.e(TAG, "onClick:Gen  " + Trackingnumber);

                    }
                    //ToDo Clear all lists after assign
                    itemAdapter.ClearRVAfterAssign();
                    ListOfBarcodesToAssign.clear();
                }else {
                    Toast.makeText(AssignItemToPackagesActivity.this, "لم يتم أضافه عناصر", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemAdapter.clearAdapterData();
    }
}