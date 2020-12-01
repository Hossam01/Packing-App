package com.example.packingapp.UI.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packingapp.UI.OrderDetails_forDriverActivity;
import com.example.packingapp.databinding.FragmentConfirmPasscodeBinding;
import com.example.packingapp.viewmodel.ConfirmPasscodeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmPasscodeFragment extends Fragment {
FragmentConfirmPasscodeBinding binding;
    private View mLeak;
    ConfirmPasscodeViewModel confirmPasscodeViewModel;
    public ConfirmPasscodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Barcode = getArguments().getString("Barcode");

       // LastOrderArry = (ArrayList<String>) getArguments().getSerializable("LastOrderIdArray");
        binding = FragmentConfirmPasscodeBinding.inflate(inflater, container, false);
        mLeak = binding.getRoot();
        confirmPasscodeViewModel= ViewModelProviders.of(this).get(ConfirmPasscodeViewModel.class);

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

    }

    public void Delete_PDNEWQTY() {

//        new AlertDialog.Builder(getActivity())
//                .setTitle(getString(R.string.want_to_delete))
//                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        databaseHelper.DeletePo_item(Barcode);
////                        FragmentManager fm=getActivity().getSupportFragmentManager();
////                        fm.popBackStack();
////                        Intent goback=new Intent(getActivity(), MainActivity.class);
////                        goback.putExtra("UserName",UserName);
////                        goback.putExtra("Branch",Branch);
////                        goback.putExtra("LastOrderId",LastOrderId);
////
////                        startActivity(goback);
//
//                        FragmentManager fm=getActivity().getSupportFragmentManager();
//                        fm.popBackStack();
//
//                        MainActivity mainActivity=(MainActivity) getActivity();
//                        if (mainActivity != null){
//                            mainActivity.CreateORUpdateRecycleView(2);
//                            Log.e("nnnnnnnnn","");
//                        }
//
//                    }
//                })
//                .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.cancel();
//
//                    }
//                }).show();

    }

    public void Edit_PDNEWQTY() {

    }
}
