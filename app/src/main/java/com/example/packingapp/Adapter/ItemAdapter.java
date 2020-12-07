package com.example.packingapp.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.packingapp.R;
import com.example.packingapp.model.GetOrderResponse.ItemsOrderDataDBDetails;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{
    private static final String TAG = "ItemAdapter";
    ArrayList<ItemsOrderDataDBDetails>dataList=new ArrayList<>();

    public void fillAdapterData(ItemsOrderDataDBDetails itemsOrderDataDBDetails) {
        //this.dataList.clear();
        this.dataList.add(itemsOrderDataDBDetails);
        Log.e(TAG, "fillAdapterData: "+  dataList.size());

        // this.dataList.add(dataList);
        notifyDataSetChanged();
    }

    public void ClearRVAfterAssign() {
        //this.dataList.clear();
        this.dataList.clear();
        Log.e(TAG, "ClearRVAfterAssign: "+  dataList.size());

        // this.dataList.add(dataList);
        notifyDataSetChanged();
    }

    public void clearAdapterData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.item_name.setText(dataList.get(position).getName());
        holder.item_qty.setText(String.valueOf(dataList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView item_name ,item_qty;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            item_name =itemView.findViewById(R.id.item_name);
            item_qty=itemView.findViewById(R.id.item_qty);
        }
    }
    public ArrayList<ItemsOrderDataDBDetails> ReturnListOfAdapter(){
        return dataList;
    }
}
