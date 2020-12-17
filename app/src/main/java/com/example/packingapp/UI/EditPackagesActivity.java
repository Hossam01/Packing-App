package com.example.packingapp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Adapter.PackedPackagesAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityEditPackagesBinding;
import com.example.packingapp.model.PackedPackageModule;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditPackagesActivity extends AppCompatActivity {

   // private List<Po_Item_of_cycleCount> po_items = new ArrayList<>();
    ActivityEditPackagesBinding binding;
    List<PackedPackageModule> Po_Item_For_Recycly;
    AppDatabase database;

    private PackedPackagesAdapter packedPackagesAdapter;
    int CountChecked;
    String TrackingnumberToEditORDelete;
    private static final String TAG = "EditPackagesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditPackagesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        database= AppDatabase.getDatabaseInstance(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CreateORUpdateRecycleView();

       // Po_Item_For_Recycly = new ArrayList<>();

//        recyclerView =  findViewById(R.id.recycle_items_view);
//
//        btn_edit=findViewById(R.id.btn_edit);
//        btn_delete=findViewById(R.id.btn_delete);

        /*Po_Item_For_Recycly.add(new Po_Item_of_cycleCount(true,"material","short text","quantity",
                "pdnewqty1","Po_unite","ean111","vendor_name"));
        Po_Item_For_Recycly.add(new Po_Item_of_cycleCount(false,"material","short text","quantity",
                "pdnewqty1","Po_unite","ean111","vendor_name"));
        Po_Item_For_Recycly.add(new Po_Item_of_cycleCount(false,"material","short text","quantity",
                "pdnewqty1","Po_unite","ean111","vendor_name"));*/


       /* databaseHelper =new DatabaseHelperForTransfer(this);

        Po_Item_For_Recycly = databaseHelper.Get_Po_Item_That_Has_PDNewQTy();

        itemDelieveredFormAdapter = new Itemtransfer_for_searchAdapter(Po_Item_For_Recycly);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemDelieveredFormAdapter);
*/


    }



    public void CreateORUpdateRecycleView(){
        Po_Item_For_Recycly = new ArrayList<>();

        Po_Item_For_Recycly=database.userDao().getAllPckages();
        packedPackagesAdapter = new PackedPackagesAdapter(Po_Item_For_Recycly);

        binding.recycleItemsView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.recycleItemsView.setLayoutManager(mLayoutManager);
        binding.recycleItemsView.setAdapter(packedPackagesAdapter);

    }

    @Override
    protected void onPause() {
        Log.e("lifecycle","onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("lifecycle","onResume");
        CreateORUpdateRecycleView();
        super.onResume();
    }


    @Override
    protected void onStop() {
        Log.e("lifecycle","onStop");
        super.onStop();

    }

    @Override
    protected void onRestart() {
        Log.e("lifecycle","onRestart");
        super.onRestart();
    }

    public void Delete_PDNEWQTY(View view) {

        List<PackedPackageModule> Trackingnumbers_List = packedPackagesAdapter.ReturnListOfPackages();
        Log.e("btn_editChecked",""+Trackingnumbers_List.size());

        CountChecked =0;
        if (Trackingnumbers_List.size() != 0) {
            for (int i = 0; i < Trackingnumbers_List.size(); i++) {
                Boolean Checked = Trackingnumbers_List.get(i).getChecked_Item();
                //Log.e("btn_editChecked",""+Checked);
                if (Checked == true) {
                    //Log.e("btn_editCheckedif",""+Checked);
                    CountChecked += 1;
                    TrackingnumberToEditORDelete = Trackingnumbers_List.get(i).getTrackingNumber();

                }
                if (i == (Trackingnumbers_List.size() - 1)) {
                    if (CountChecked < 1 || CountChecked > 1) {
                        Toast.makeText(EditPackagesActivity.this, R.string.you_choice_mulite_or_choice_noting, Toast.LENGTH_LONG).show();
                    } else if (CountChecked == 1) {  //&& !BarCodeChecked.isEmpty()
                        new AlertDialog.Builder(this)
                                .setTitle(getString(R.string.delete_dialoge))
                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        database.userDao().DeleteTrackingNumber(TrackingnumberToEditORDelete);
//                                        database.userDao().DeleteTrackingNumberFromtrackingtable(TrackingnumberToEditORDelete);
                                        int UID_to_delete =database.userDao().GetUID(TrackingnumberToEditORDelete);
                                        Log.e(TAG, "onClick: UID_to_delete "+UID_to_delete );
                                        List<String> sku_to_remove =database.userDao().getskuOfTrackingNumber(TrackingnumberToEditORDelete);
                                        Log.e(TAG, "onClick:sku_to_remove "+sku_to_remove.size() );
                                        List<String> AfterTrackingNumberDeleted_list=database.userDao().
                                                GetTrackingNumbersAfterDeleteOne(TrackingnumberToEditORDelete);
                                        Log.e(TAG, "onClick:LastTrackingNumber "+AfterTrackingNumberDeleted_list.size() );

                                        List<Integer> NewTrackingNumber_NUM=database.userDao().
                                                GetNewTrackingNumbersAfterDeleteOne(TrackingnumberToEditORDelete);
                                        Log.e(TAG, "onClick:NewTrackingNumber_NUM "+NewTrackingNumber_NUM.size() );
                                        List<String> NewTrackingNumber=new ArrayList<>();

                                        for (int i=0;i<NewTrackingNumber_NUM.size();i++) {
                                            int num=NewTrackingNumber_NUM.get(i);
                                            if (num < 10) {
                                                String Trackingnumber = database.userDao().getOrderNumber() + "-0" + num;
                                                NewTrackingNumber.add(Trackingnumber);
                                                Log.e(TAG, "onClick:NewTrackingNumber "+NewTrackingNumber.get(i));
//                                                database.userDao().Insertrackingnumber(new TrackingnumbersListDB(Trackingnumber));
//                                                database.userDao().updatetrackingnumberforListOfItems(Trackingnumber, ListOfBarcodesToAssign);
                                                database.userDao().updatetrackingnumberAfterDeleteOne_Details(Trackingnumber, AfterTrackingNumberDeleted_list.get(i));
                                                database.userDao().updatetrackingnumberAfterDeleteOne_ListDB(Trackingnumber, AfterTrackingNumberDeleted_list.get(i));
                                                Log.e(TAG, "onClick:Trackingnumber "+Trackingnumber );
                                                Log.e(TAG, "onClick:LastTrackingNumber "+AfterTrackingNumberDeleted_list.get(i) );
                                            } else {
                                                String Trackingnumber = database.userDao().getOrderNumber() + "-" + num;
                                                NewTrackingNumber.add(Trackingnumber);
                                                Log.e(TAG, "onClick:NewTrackingNumber "+NewTrackingNumber.get(i) );
                                                //TODO can we do update for list after finish using NewTrackingNumber list
                                                database.userDao().updatetrackingnumberAfterDeleteOne_Details(Trackingnumber, AfterTrackingNumberDeleted_list.get(i));
                                                database.userDao().updatetrackingnumberAfterDeleteOne_ListDB(Trackingnumber, AfterTrackingNumberDeleted_list.get(i));
                                                Log.e(TAG, "onClick:Trackingnumber "+Trackingnumber );
                                                Log.e(TAG, "onClick:LastTrackingNumber "+AfterTrackingNumberDeleted_list.get(i) );

//                                                database.userDao().Insertrackingnumber(new TrackingnumbersListDB(Trackingnumber));
//                                                database.userDao().updatetrackingnumberforListOfItems(Trackingnumber, ListOfBarcodesToAssign);
                                            }
                                        }

                                        database.userDao().DeleteTrackingNumberFromtrackingtable_using_uid(UID_to_delete);
                                        database.userDao().DeleteTrackingNumberFromDetailstable_using_sku(null , sku_to_remove);
                                        CreateORUpdateRecycleView();
//                                        packedPackagesAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }

                } /*else
                    Toast.makeText(EditPackagesActivity.this, "لايوجد بيانات للادخال", Toast.LENGTH_SHORT).show();
                    */
            }
            }

    }

    public void Edit_PDNEWQTY(View view) {

        List<PackedPackageModule> Trackingnumbers_List = packedPackagesAdapter.ReturnListOfPackages();
        Log.e("btn_editChecked", "" + Trackingnumbers_List.size());

        CountChecked = 0;
        if (Trackingnumbers_List.size() != 0) {
            for (int i = 0; i < Trackingnumbers_List.size(); i++) {
                Boolean Checked = Trackingnumbers_List.get(i).getChecked_Item();
                //Log.e("btn_editChecked",""+Checked);
                if (Checked == true) {
                    //Log.e("btn_editCheckedif",""+Checked);
                    CountChecked += 1;
                    TrackingnumberToEditORDelete = Trackingnumbers_List.get(i).getTrackingNumber();

                }
                if (i == (Trackingnumbers_List.size() - 1)) {
                    if (CountChecked < 1 || CountChecked > 1) {
                        Toast.makeText(EditPackagesActivity.this, R.string.you_choice_mulite_or_choice_noting, Toast.LENGTH_LONG).show();
                    } else if (CountChecked == 1) {  //&& !BarCodeChecked.isEmpty()
                        Log.e(TAG, "Edit_PDNEWQTY: "+TrackingnumberToEditORDelete );
                        Intent GoToShowItemsOfPackages = new Intent(EditPackagesActivity.this, EditPackageItemsActivity.class);
                        GoToShowItemsOfPackages.putExtra("TrackingNumber", TrackingnumberToEditORDelete);
                        startActivity(GoToShowItemsOfPackages);

                    }

                } /*else
                 //   Toast.makeText(EditPackagesActivity.this, "لايوجد بيانات للادخال", Toast.LENGTH_SHORT).show();*/
            }
        }
    }
    @Override
    public void onBackPressed() {
//        Intent Go_Back= new Intent(com.example.connecttosoapapiapp.ReceivingModule.CheckItemFormActivity.this,ScanRecievingActivity.class);
//        Go_Back.putExtra("This Is First Time",false);
//
//        startActivity(Go_Back);
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
