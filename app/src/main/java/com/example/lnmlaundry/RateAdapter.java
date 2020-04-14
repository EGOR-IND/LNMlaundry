package com.example.lnmlaundry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.MyViewHolder> {
    public ArrayList<RateModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item, rate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView.findViewById(R.id.itenName);
            this.rate = itemView.findViewById(R.id.itemRate);
        }
    }

    public RateAdapter(ArrayList<RateModel> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rates_structure, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        TextView item = holder.item;
        TextView rate = holder.rate;

        item.setText(dataSet.get(position).getItem());
        rate.setText(dataSet.get(position).getRate().toString());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
