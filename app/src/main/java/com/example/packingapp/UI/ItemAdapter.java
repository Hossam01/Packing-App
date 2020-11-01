package com.example.packingapp.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packingapp.R;
import com.example.packingapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{

    ArrayList<Product>dataList=new ArrayList<>();

    public void fillAdapterData(Product dataList) {
        //this.dataList.clear();
        this.dataList.add(dataList);
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
        holder.view.setText(dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView view;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
           view =itemView.findViewById(R.id.text);
        }


    }
}
