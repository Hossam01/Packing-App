package com.example.packingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    String AddNewPackageORAddForExistPackage="";
    List<String> ListOfBarcodesToAssign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignItemsToPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=AppDatabase.getDatabaseInstance(this);

        ListOfBarcodesToAssign=new ArrayList<>();
        if (getIntent().getExtras() !=null) {
            AddNewPackageORAddForExistPackage = getIntent().getExtras().getString("AddNewPackageORAddForExistPackage");
        }else {
            Toast.makeText(this, "AddNewPackageORAddForExistPackage is null", Toast.LENGTH_SHORT).show();
        }

        itemAdapter = new ItemAdapter();
        binding.recyclerView.setAdapter(itemAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.imagSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SearchOfBarcode();
            }
        });

        binding.editBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent == null
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_DPAD_CENTER){
                    SearchOfBarcode();
                }
                return false;
            }
        });


        binding.btnAssignItemsToPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List <ItemsOrderDataDBDetails> Adapterlist = itemAdapter.ReturnListOfAdapter();

                if (Adapterlist.size() >0 && ListOfBarcodesToAssign.size() >0) {
                    if (AddNewPackageORAddForExistPackage.equalsIgnoreCase("New")) {
//                    Log.e(TAG, "onClick: " + database.userDao().getLastTrackingnumber().getTrackingNumber());
                        if (database.userDao().getLastTrackingnumber() == null) {
                            Log.e(TAG, "onClick:ordernum  " + database.userDao().getOrderNumber() + "-01");
                            String Trackingnumber = database.userDao().getOrderNumber() + "-01";
                            database.userDao().Insertrackingnumber(new TrackingnumbersListDB(database.userDao().getOrderNumber() + "-01"));
                            database.userDao().updatetrackingnumberforListOfItems(Trackingnumber, ListOfBarcodesToAssign);
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
                                database.userDao().updatetrackingnumberforListOfItems(Trackingnumber, ListOfBarcodesToAssign);
                            } else {
                                Trackingnumber = database.userDao().getOrderNumber() + "-" + num;
                                database.userDao().Insertrackingnumber(new TrackingnumbersListDB(Trackingnumber));
                                database.userDao().updatetrackingnumberforListOfItems(Trackingnumber, ListOfBarcodesToAssign);


                            }
                            Log.e(TAG, "onClick:Gen  " + Trackingnumber);

                        }
                        //ToDo Clear all lists after assign
                        itemAdapter.ClearRVAfterAssign();
                        ListOfBarcodesToAssign.clear();
                    }else {
                        Toast.makeText(AssignItemToPackagesActivity.this, getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
                        database.userDao().updatetrackingnumberforListOfItems(AddNewPackageORAddForExistPackage, ListOfBarcodesToAssign);
                        //ToDo Clear all lists after assign
                        Intent GoBackToEditItemsOfPackage=new Intent(AssignItemToPackagesActivity.this,EditPackageItemsActivity.class);
                        GoBackToEditItemsOfPackage.putExtra("TrackingNumber",AddNewPackageORAddForExistPackage);
                        startActivity(GoBackToEditItemsOfPackage);
                        finish();
                    }
                }else {
                    Toast.makeText(AssignItemToPackagesActivity.this, getResources().getString(R.string.notadd), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SearchOfBarcode() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemAdapter.clearAdapterData();
    }
}