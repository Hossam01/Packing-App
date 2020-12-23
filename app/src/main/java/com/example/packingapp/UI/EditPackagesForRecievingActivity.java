package com.example.packingapp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packingapp.Adapter.RecievedPackagesAdapter;
import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.Helper.Constant;
import com.example.packingapp.R;
import com.example.packingapp.databinding.ActivityEditPackagesForRecievingBinding;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.RecievedPackageModule;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditPackagesForRecievingActivity extends AppCompatActivity {
    private static final String TAG = "EditPackagesForRecievin";
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

        binding.editTrackingnumber.requestFocus();

        Po_Item_For_Recycly = new ArrayList<>();
        Po_Item_For_Recycly=database.userDao().getAllRecievedPackages();
        CreateORUpdateRecycleView(Po_Item_For_Recycly);

        binding.editTrackingnumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                    LoadNewPurchaseOrder();
                }
                return false;
            }
        });
        binding.btnLoadingNewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadNewPurchaseOrder();
            }
        });

    }

    private void LoadNewPurchaseOrder() {
        String OrderNumber;
        if (!binding.editTrackingnumber.getText().toString().isEmpty() &&
                binding.editTrackingnumber.getText().toString().contains("-")) {
            //  if (!my_match.find()) {
            if (Constant.RegulerExpre_forTrackingNumbeer(binding.editTrackingnumber.getText().toString())) {
                // Toast.makeText(context, "Special character not found in the string", Toast.LENGTH_SHORT).show();
                OrderNumber =
                        binding.editTrackingnumber.getText().toString().substring(0,
                                binding.editTrackingnumber.getText().toString().indexOf("-"));
                //TODO CHECK IF TRACKING NUMBER MORE THAN require no of packages
                String NOtrackingnumber = binding.editTrackingnumber.getText().toString().substring(
                        binding.editTrackingnumber.getText().toString().indexOf("-") + 1);
                Log.e(TAG, "onClick: " + NOtrackingnumber);

                List<RecievePackedModule> recievePackedlist = database.userDao().getRecievePacked_ORDER_NO(OrderNumber);
                Log.e(TAG, "onClick: OrdrecievePackedlist " + recievePackedlist.size());

                if (recievePackedlist.size() > 0) {
                    Po_Item_For_Recycly.clear();
                    Po_Item_For_Recycly = database.userDao().getRecievePacked_Tracking_Number_ForSearch(binding.editTrackingnumber.getText().toString());
                    CreateORUpdateRecycleView(Po_Item_For_Recycly);
                } else {
                    binding.editTrackingnumber.setError("هذا السيريال لم يتم أدخاله من قبل");
                    binding.editTrackingnumber.requestFocus();
//                    binding.editTrackingnumber.setText("");

                }
            }else {
                Toast.makeText(EditPackagesForRecievingActivity.this, "Special character found in the string", Toast.LENGTH_SHORT).show();
            }
        }else {
            binding.editTrackingnumber.setError(getString(R.string.enter_tracking_number));
//            binding.editTrackingnumber.setText("");
            binding.editTrackingnumber.requestFocus();
        }
    }

    public void CreateORUpdateRecycleView(List<RecievedPackageModule> Po_Item_For_Recycly1){

        recievedPackagesAdapter = new RecievedPackagesAdapter(Po_Item_For_Recycly1);

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
                                        database.userDao().deleteRecievePackedModule_ForTrackingNumber(TrackingnumberToEditORDelete);
                                        Po_Item_For_Recycly.clear();
                                        Po_Item_For_Recycly=database.userDao().getAllRecievedPackages();
                                        CreateORUpdateRecycleView(Po_Item_For_Recycly);
                                        binding.editTrackingnumber.setText("");
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