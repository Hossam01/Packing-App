package com.example.packingapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.packingapp.R;
import com.example.packingapp.model.DriverModules.DriverPackages_Header_DB;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DriverOrdersAdapter extends RecyclerView.Adapter<DriverOrdersAdapter.MyViewHolder> {

    private List<DriverPackages_Header_DB> ItemsList;
    DriverPackages_Header_DB packages_db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_nu , txt_order_number , txt_phone_number;
        LinearLayout main_linear_of_item;
        public MyViewHolder(View view) {
            super(view);
            txt_nu=  view.findViewById(R.id.txt_nu);
            txt_order_number =  view.findViewById(R.id.txt_order_number);
            txt_phone_number=view.findViewById(R.id.txt_phone_number);
            main_linear_of_item=view.findViewById(R.id.main_linear_of_item);
        }
    }


    public DriverOrdersAdapter(List<DriverPackages_Header_DB> moviesList) {
        this.ItemsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_for_driver_orders, parent, false);

        return new MyViewHolder(itemView);
    }

   // @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        packages_db = ItemsList.get(position);

        holder.txt_nu.setText(""+(position+1));

        holder.txt_order_number.setText(packages_db.getOrder_number());
        holder.txt_phone_number.setText(packages_db.getCustomer_phone());
        //android:textIsSelectable="true"
//        holder.txt_ean11.setTextIsSelectable(true);
//
//        holder.txt_nu.requestFocus();
       // Log.e("btn_editChecked000",""+ItemsList.size());

    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public List<DriverPackages_Header_DB> ReturnListOfPackages(){


        return ItemsList;

    }
}
