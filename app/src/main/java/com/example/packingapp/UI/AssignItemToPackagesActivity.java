package com.example.packingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
            List<ItemsOrderDataDBDetails> itemsOrderDataDBDetailsList =new ArrayList<>();
            String KQTY,GQTY,TotalQTYFor23 , BarcodeFor23  , Depart;
            KQTY="00";
            GQTY="000";
            TotalQTYFor23="";
            BarcodeFor23="";
            Depart=binding.editBarcode.getText().toString().substring(0,2);
            if (Depart.equalsIgnoreCase("23")
                    && binding.editBarcode.getText().toString().length() ==13) {
                KQTY = binding.editBarcode.getText().toString().substring(7, 9);
                GQTY = binding.editBarcode.getText().toString().substring(9, 12);
                TotalQTYFor23 = KQTY + "." + GQTY;
                //BarcodeFor23 = et_Barcode.getText().toString().replace(TotalQTYFor23.replace(".", ""), "00000");
                BarcodeFor23 = binding.editBarcode.getText().toString().substring(0, 7);
                itemsOrderDataDBDetailsList = database.userDao().getItem_scales(BarcodeFor23+"%");
                Calculatcheckdigitforscales(binding.editBarcode.getText().toString().substring(0,7)+"00000");

            }else {
                itemsOrderDataDBDetailsList = database.userDao().getItem(binding.editBarcode.getText().toString());
//                    product = new Product("laptop", "5");
            }
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
                    binding.editBarcode.setError(getResources().getString(R.string.enterbefor));
                    binding.editBarcode.requestFocus();
                }

            }else {
                Toast.makeText(AssignItemToPackagesActivity.this, getResources().getString(R.string.enter_valid_barcode), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), GetOrderDatactivity.class);
        startActivity(i);
    }

    private String Calculatcheckdigitforscales(String toString) {
        String Barcode;
        int  chkdigit;
        Log.e("zzzbarodd1 ",""+toString.charAt(1));
        Log.e("zzzbarodd1 ",""+Integer.valueOf(toString.charAt(1)));
        Log.e("zzzbarodd1.2 ",""+Integer.valueOf(toString.substring(1,2)));
        Log.e("zzzbarodd11 ",""+toString.charAt(11));
        Log.e("zzzbarodd11.12 ",""+Integer.valueOf(toString.substring(11,12)));
        int odd  = Integer.valueOf(toString.substring(1,2)) + Integer.valueOf(toString.substring(3,4)) + Integer.valueOf(toString.substring(5,6)) + Integer.valueOf(toString.substring(7,8)) + Integer.valueOf(toString.substring(9,10)) + Integer.valueOf(toString.substring(11,12));
        int eveen  = Integer.valueOf(toString.substring(0,1)) + Integer.valueOf(toString.substring(2,3)) + Integer.valueOf(toString.substring(4,5)) + Integer.valueOf(toString.substring(6,7)) + Integer.valueOf(toString.substring(8,9)) + Integer.valueOf(toString.substring(10,11));

        Log.e("zzzbarodd",""+odd);
        Log.e("zzzbareveen",""+eveen);
        if ((((odd * 3) + eveen) % 10) != 0 )
            chkdigit = 10 - (((odd * 3) + eveen) % 10) ;
        else
            chkdigit = 0 ;

        Barcode=toString +chkdigit;
        Log.e("zzzbarcode",""+Barcode);
        return Barcode;
    }
}