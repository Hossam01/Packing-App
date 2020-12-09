package com.example.packingapp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.packingapp.Adapter.RecievedPackagesAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityEditPackagesForRecievingBinding;
import com.example.packingapp.model.RecievedPackageModule;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditPackagesForRecievingActivity extends AppCompatActivity {
ActivityEditPackagesForRecievingBinding binding;
    List<RecievedPackageModule> Po_Item_For_Recycly;
    AppDatabase database;
    private RecievedPackagesAdapter recievedPackagesAdapter;
    int CountChecked;
    String TrackingnumberToEditORDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityEditPackagesForRecievingBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        database= AppDatabase.getDatabaseInstance(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CreateORUpdateRecycleView();
    }

    public void CreateORUpdateRecycleView(){
        Po_Item_For_Recycly = new ArrayList<>();

        Po_Item_For_Recycly=database.userDao().getAllRecievedPackages();
        recievedPackagesAdapter = new RecievedPackagesAdapter(Po_Item_For_Recycly);

        binding.recycleItemsView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.recycleItemsView.setLayoutManager(mLayoutManager);
        binding.recycleItemsView.setAdapter(recievedPackagesAdapter);

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

        List<RecievedPackageModule> Trackingnumbers_List = recievedPackagesAdapter.ReturnListOfPackages();
        Log.e("btn_editChecked",""+Trackingnumbers_List.size());

        CountChecked =0;
        if (Trackingnumbers_List.size() != 0) {
            for (int i = 0; i < Trackingnumbers_List.size(); i++) {
                Boolean Checked = Trackingnumbers_List.get(i).getChecked_Item();
                //Log.e("btn_editChecked",""+Checked);
                if (Checked == true) {
                    //Log.e("btn_editCheckedif",""+Checked);
                    CountChecked += 1;
                    TrackingnumberToEditORDelete = Trackingnumbers_List.get(i).getTracking_Number();

                }
                if (i == (Trackingnumbers_List.size() - 1)) {
                    if (CountChecked < 1 || CountChecked > 1) {
                        Toast.makeText(EditPackagesForRecievingActivity.this, R.string.you_choice_mulite_or_choice_noting, Toast.LENGTH_LONG).show();
                    } else if (CountChecked == 1) {  //&& !BarCodeChecked.isEmpty()
                        new AlertDialog.Builder(this)
                                .setTitle(getString(R.string.delete_dialoge))
                                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        database.userDao().deleteRecievePackedModule_ForTrackingNumber("'"+TrackingnumberToEditORDelete+"'");

                                        CreateORUpdateRecycleView();
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
}