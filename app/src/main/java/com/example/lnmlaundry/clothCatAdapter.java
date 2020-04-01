package com.example.lnmlaundry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class clothCatAdapter extends RecyclerView.Adapter<clothCatAdapter.MyViewHolder> {
    public ArrayList<clothCat> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clothCat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.clothCat = itemView.findViewById(R.id.clothCat);
        }
    }

    public clothCatAdapter(ArrayList<clothCat> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_cat_count_card, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView clothCat = holder.clothCat;

        clothCat.setText(dataSet.get(position).getClothCategory());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
