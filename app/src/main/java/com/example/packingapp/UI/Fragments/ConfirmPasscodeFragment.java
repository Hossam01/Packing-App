package com.example.packingapp.UI.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.packingapp.Database.AppDatabase;
import com.example.packingapp.UI.OrderDetails_forDriverActivity;
import com.example.packingapp.databinding.FragmentConfirmPasscodeBinding;
import com.example.packingapp.model.DriverModules.DriverPackages_Details_DB;
import com.example.packingapp.model.GetOrderResponse.OrderDataModuleDBHeader;
import com.example.packingapp.model.RecievePacked.RecievePackedModule;
import com.example.packingapp.model.ResponseUpdateStatus;
import com.example.packingapp.viewmodel.ConfirmPasscodeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmPasscodeFragment extends Fragment {
FragmentConfirmPasscodeBinding binding;
    private View mLeak;
    ConfirmPasscodeViewModel confirmPasscodeViewModel;
    AppDatabase database;
    String Orderclicked="";
    private static final String TAG = "ConfirmPasscodeFragment";
    String Passcode="";
    public ConfirmPasscodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() !=null) {
            Orderclicked = getArguments().getString("Orderclicked");
        }else {
            Toast.makeText(getActivity(), "Orderclicked  Not founf", Toast.LENGTH_SHORT).show();
        }
       // LastOrderArry = (ArrayList<String>) getArguments().getSerializable("LastOrderIdArray");
        binding = FragmentConfirmPasscodeBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        confirmPasscodeViewModel= ViewModelProviders.of(this).get(ConfirmPasscodeViewModel.class);

        database= AppDatabase.getDatabaseInstance(getActivity());

        return mLeak;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        btn_edit=view.findViewById(R.id.btn_edit);
       /* btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_PDNEWQTY();
            }
        });
        btn_delete=view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_PDNEWQTY();
            }
        });*/
         //databaseHelper=new DatabaseHelper(getActivity());

//            Intent goback=new Intent(getActivity(), MainActivity.class);
//            goback.putExtra("UserName",UserName);
//            goback.putExtra("Branch",Branch);
//            goback.putExtra("LastOrderId",LastOrderId);
//
//            startActivity(goback);

        Passcode= database.userDao().getPasscode(Orderclicked);

        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.popBackStack();

                OrderDetails_forDriverActivity orderDetails_forDriverActivity=(OrderDetails_forDriverActivity) getActivity();
                if (orderDetails_forDriverActivity != null){
                 //   mainActivity.CreateORUpdateRecycleView(2);
                    Log.e("nnnnnnnnn","");
                }
//                Intent goback=new Intent(getActivity(), MainActivity.class);
//                goback.putExtra("UserName",UserName);
//                goback.putExtra("Branch",Branch);
//                goback.putExtra("LastOrderId",LastOrderId);
//
//                startActivity(goback);
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //  Rejected under inspection
                //Reschedule
                //Has-Been-Delivered
               if (Passcode.equalsIgnoreCase(binding.editPasscode.getText().toString())) {
                   //get list of packages that rejected by checking for reason equal nullir empty
                   List<DriverPackages_Details_DB> driverPackages_details_dbList_rejected  =database.userDao().getAllPckagesForReject();
                   // update status
                   database.userDao().UpdatestatusForNotRejectWhenClickConfirmed(Orderclicked,"Has-Been-Delivered");

                   List<DriverPackages_Details_DB> driverPackages_details_dbList  =database.userDao().getAllPckagesForUpload(Orderclicked);
                   Log.e(TAG, "onClick: "+driverPackages_details_dbList.size() );

                   if (driverPackages_details_dbList_rejected.size() >0 ){
                       UpdateStatus_Passcode_Header_ON_83("Rejected under inspection");
                       //TODO list for tracking number and reason and status for details _check file name
                       UpdateStatus_Reason_Details_ON_83(driverPackages_details_dbList);
                       UpdateStatus("rejected_under_inspection");
                   }else {
                       UpdateStatus_Passcode_Header_ON_83("Has-Been-Delivered");
                       //TODO list for tracking number and reason and status for details
                         UpdateStatus_Reason_Details_ON_83(driverPackages_details_dbList);
                       UpdateStatus("has_been_delivered");
                   }

//                   OrderDetails_forDriverActivity orderDetails_forDriverActivity = (OrderDetails_forDriverActivity) getActivity();
//                   if (orderDetails_forDriverActivity != null) {
//                       //   mainActivity.CreateORUpdateRecycleView(2);
//                       Log.e("nnnnnnnnn", "");
//                   }
               }else {
                   binding.editPasscode.setError("Incorrect passcode");
               }

//                FragmentManager fm=getActivity().getSupportFragmentManager();
//                fm.popBackStack();
//
//                OrderDetails_forDriverActivity orderDetails_forDriverActivity=(OrderDetails_forDriverActivity) getActivity();
//                if (orderDetails_forDriverActivity != null){
//                    //   mainActivity.CreateORUpdateRecycleView(2);
//                    Log.e("nnnnnnnnn","");
//                }
//                Intent goback=new Intent(getActivity(), MainActivity.class);
//                goback.putExtra("UserName",UserName);
//                goback.putExtra("Branch",Branch);
//                goback.putExtra("LastOrderId",LastOrderId);
//
//                startActivity(goback);
            }
        });

    }

    public void UpdateStatus_Passcode_Header_ON_83(String Status) {
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();
        if (Orderclicked != null) {
            confirmPasscodeViewModel.UpdateOrderStatus_Passcode_Header_ON_83(
                    Orderclicked,
                    Passcode, Status
            );
            Log.e(TAG, "UpdateStatus_zone_ON_83 zzzo : " + Orderclicked);
            Log.e(TAG, "UpdateStatus_zone_ON_83 zzzpa : " + Passcode);
            Log.e(TAG, "UpdateStatus_zone_ON_83 zzzsta : " + Status);

            confirmPasscodeViewModel.mutableLiveData_UpdateStatus_PASSCODE_ON_83.observe(getViewLifecycleOwner(), new Observer<ResponseUpdateStatus>() {
                @Override
                public void onChanged(ResponseUpdateStatus message) {
                //    Toast.makeText(getActivity(), "ConfirmedH", Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                    Log.e(TAG, "onChanged:update " + message.getMessage());
                }
            });
        } else {
            Toast.makeText(getActivity(), "لم الرفع .. أضغط مره أخرى ", Toast.LENGTH_SHORT).show();
        }

    }

    public void UpdateStatus_Reason_Details_ON_83(List<DriverPackages_Details_DB> driverPackages_details_dbList) {
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        List<RecievePackedModule> orderDataModuleDBHeaderkist = database.userDao().getorderNORecievePackedModule();

        if (Orderclicked != null) {
            confirmPasscodeViewModel.UpdateOrderStatus_Reason_Details_ON_83(driverPackages_details_dbList);
            Log.e(TAG, "UpdateStatus_zone_ON_83 ErrorDet : " + driverPackages_details_dbList.size());

            confirmPasscodeViewModel.mutableLiveData_UpdateStatus_Reason_ON_83.observe(getViewLifecycleOwner(), new Observer<ResponseUpdateStatus>() {
                @Override
                public void onChanged(ResponseUpdateStatus message) {
                    Toast.makeText(getActivity(), "ConfirmedD", Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                    Log.e(TAG, "onChanged:updateErrorDet " + message.getMessage());
                }
            });
        } else {
            Toast.makeText(getActivity(), "لم الرفع .. أضغط مره أخرى ", Toast.LENGTH_SHORT).show();
        }

    }


    public void UpdateStatus(String status){
//        if (database.userDao().getAllItemsWithoutTrackingnumber().size() == 0){
        OrderDataModuleDBHeader orderDataModuleDBHeader = database.userDao().getHeaderToUpload();
        confirmPasscodeViewModel.UpdateStatus(
                Orderclicked,
                status
        );
        confirmPasscodeViewModel.mutableLiveData_UpdateStatus.observe(getActivity(), new Observer<ResponseUpdateStatus>() {
            @Override
            public void onChanged(ResponseUpdateStatus message) {
                Toast.makeText(getActivity(), ""+message.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onChanged: "+message.getMessage() );
            }
        });
//        }else {
//            Toast.makeText(GetOrderDatactivity.this, "توجد عناصر لم يتم تعبئتها", Toast.LENGTH_SHORT).show();
//        }
    }

}
