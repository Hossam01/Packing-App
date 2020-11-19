package com.example.packingapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.packingapp.R;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;
import com.example.packingapp.model.PackedPackageItemsModule;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PackedPackageItemsAdapter extends RecyclerView.Adapter<PackedPackageItemsAdapter.MyViewHolder> {

    private List<PackedPackageItemsModule> ItemsList;
    PackedPackageItemsModule packedPackageItemsModule;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public TextView txt_nu , txt_barcode,txt_qty;
        LinearLayout main_linear_of_item;
        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_item);
            txt_nu=  view.findViewById(R.id.txt_nu);
            txt_barcode =  view.findViewById(R.id.txt_barcode);
            txt_qty=view.findViewById(R.id.txt_qty);
            main_linear_of_item=view.findViewById(R.id.main_linear_of_item);
        }
    }


    public PackedPackageItemsAdapter(List<PackedPackageItemsModule> moviesList) {
        this.ItemsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_for_packed_package_items, parent, false);

        return new MyViewHolder(itemView);
    }

   // @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        packedPackageItemsModule = ItemsList.get(position);
         if (packedPackageItemsModule.getChecked_Item() ==null){
             packedPackageItemsModule.setChecked_Item(false);
         }
        if (packedPackageItemsModule.getChecked_Item()){
            holder.checkBox.setChecked(true);
  //          holder.main_linear_of_item.setBackgroundColor(R.color.red);
        }else {
            holder.checkBox.setChecked(false);
  //          holder.main_linear_of_item.setBackgroundColor(R.color.third_white);

        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
      //      @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    //po_item.setChecked_Item(true);
                    ItemsList.get(position).setChecked_Item(true);
                  //  Log.e("editChecked",""+ItemsList.get(position).getMATERIAL1());
    //                holder.main_linear_of_item.setBackgroundColor(R.color.red);

                }else if (!holder.checkBox.isChecked()){
                    //po_item.setChecked_Item(false);
                    ItemsList.get(position).setChecked_Item(false);
    //                holder.main_linear_of_item.setBackgroundColor(R.color.third_white);
                }
            }
        });

        holder.checkBox.setVisibility(View.VISIBLE);
        holder.txt_nu.setText(""+(position+1));
        holder.txt_qty.setText(packedPackageItemsModule.getQuantity());
        holder.txt_barcode.setText(packedPackageItemsModule.getSku());

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

    public List<PackedPackageItemsModule> ReturnListOfPackages(){


        return ItemsList;

    }
}
