package com.example.packingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Adapter.PackedPackageItemsAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityEditPackageBinding;
import com.example.packingapp.model.PackedPackageItemsModule;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditPackageItemsActivity extends AppCompatActivity {
    AppDatabase database;
    List<PackedPackageItemsModule> Po_Item_For_Recycly;
    private PackedPackageItemsAdapter packedPackageItemsAdapter;
    int CountChecked;
    String BarcodeToEditORDelete;
    ActivityEditPackageBinding binding;
    String TrackingNumber="";
    private static final String TAG = "EditPackageItemsActivit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database= AppDatabase.getDatabaseInstance(this);
        Intent getData= getIntent();
        if (getData.getExtras().getString("TrackingNumber") != null){
            TrackingNumber = getData.getExtras().getString("TrackingNumber");
        }else {
            TrackingNumber="";
        }
        Log.e(TAG, "onCreate: "+ TrackingNumber);
        /*database.userDao().getAllItem(binding.item.getText().toString()).subscribeOn(Schedulers.computation())
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
        });*/

        CreateORUpdateRecycleView();
    }

    public void CreateORUpdateRecycleView(){
        Po_Item_For_Recycly = new ArrayList<PackedPackageItemsModule>();

        Po_Item_For_Recycly=database.userDao().getItemsOfTrackingNumber(TrackingNumber);
        packedPackageItemsAdapter = new PackedPackageItemsAdapter(Po_Item_For_Recycly);

        binding.recycleItemsView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.recycleItemsView.setLayoutManager(mLayoutManager);
        binding.recycleItemsView.setAdapter(packedPackageItemsAdapter);

    }

    public void Delete_PDNEWQTY(View view) {

        List<PackedPackageItemsModule> Barcodes_List = packedPackageItemsAdapter.ReturnListOfPackages();
        Log.e("btn_editChecked",""+Barcodes_List.size());

        CountChecked =0;
        if (Barcodes_List.size() != 0) {
            for (int i = 0; i < Barcodes_List.size(); i++) {
                Boolean Checked = Barcodes_List.get(i).getChecked_Item();
                //Log.e("btn_editChecked",""+Checked);
                if (Checked == true) {
                    //Log.e("btn_editCheckedif",""+Checked);
                    CountChecked += 1;
                    BarcodeToEditORDelete = Barcodes_List.get(i).getSku();

                }
                if (i == (Barcodes_List.size() - 1)) {
                    if (CountChecked < 1 || CountChecked > 1) {
                        Toast.makeText(EditPackageItemsActivity.this, "لقد اخترت اكثر من اختيار أو لم تختار شى", Toast.LENGTH_LONG).show();
                    } else if (CountChecked == 1) {  //&& !BarCodeChecked.isEmpty()
                        new AlertDialog.Builder(this)
                                .setTitle(getString(R.string.delete_dialoge))
                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        database.userDao().DeleteTrackingNumberForItem(BarcodeToEditORDelete);

                                        CreateORUpdateRecycleView();
                                    }
                                })
                                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }

                } else
                    Toast.makeText(EditPackageItemsActivity.this, "لايوجد بيانات للادخال", Toast.LENGTH_SHORT).show();
            }
        }

    }
}